package co.in.sixdee.middleware.fw.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.sixdee.util.ConnectionPool;

import co.in.sixdee.middleware.fw.adapter.flowone.Response;
import co.in.sixdee.middleware.fw.audit.orderLifecycleEvents.FOCallBakProcessThread;
import co.in.sixdee.middleware.fw.audit.orderLifecycleEvents.OrderEventAuditUtil;
import co.in.sixdee.middleware.fw.bean.GenericDTOResponseObject;
import co.in.sixdee.middleware.fw.bo.SyncProcessBO;
import co.in.sixdee.middleware.fw.cdr.bo.CDRWritingBO;
import co.in.sixdee.middleware.fw.config.ThreadInitiator;
import co.in.sixdee.middleware.fw.dto.GenericDTO;
import co.in.sixdee.middleware.fw.dto.NGTableDataDTO;
import co.in.sixdee.middleware.fw.dto.workflow.WorkItemInfo;
import co.in.sixdee.middleware.fw.hibernate.HibernateUtils;
import co.in.sixdee.middleware.ordermanagement.abs.AbstractHandler;
import co.in.sixdee.middleware.ordermanagement.hibernate.tableconfigs.EventTypesConfig;
import co.in.sixdee.middleware.ordermanagement.util.WorkFlowUtil;
import co.in.sixdee.middleware.ordermanagement.util.WorkflowConstants;
import co.in.sixdee.middleware.ordermanagement.util.exception.CommonException;
import co.in.sixdee.middleware.ordermanagement.util.handlerutilities.OrderManagerUtility;

public class FlowOneUtility extends AbstractHandler {

	private static Logger _logger = LogManager.getLogger(FlowOneUtility.class.getName());
	private static final String PROPFILE = "MNP_PROPERTIES";
	String logFormat = "";

	public FlowOneUtility() {

	}

	public FlowOneUtility(GenericDTO genericDTO) {
		init(genericDTO);
		logFormat = new StringBuilder().append(" | ").append(genericDTO.getTransactionId()).append(" | ").toString();
	}

	public void processCallBackResponse(Response response, GenericDTO genericDTO) {

		SyncProcessBO syncProcess = null;
		OrderManagerUtility orderManagerUtility = null;
		GetDataFromCache cache = null;
		long interval = 30;
		boolean isFOAuditRequired = false;
		boolean isFOEventWaiting = false;
		int maxRetry = 4;
		int configuredMaxRetry = 4;
		String action = null;
		long checkEventStartTime = 0;

		try {
			
			//genericDTO.getStrAttributes().put(FlowOneConstants.hasFlowoneCallBack, "true");
			
			syncProcess = new SyncProcessBO();
	
			orderManagerUtility = new OrderManagerUtility(genericDTO);
			cache = new GetDataFromCache();
			if (cache.getCacheDetailsFromProp("SYSCONFIG","FO_ASYNCH_PROCESS_DELAY") != null) {
				interval = Long.valueOf(cache.getCacheDetailsFromProp("SYSCONFIG","FO_ASYNCH_PROCESS_DELAY"));
				
			}
			if (CommonUtil.validateField(cache.getCacheDetailsFromProp("SYSCONFIG","FO_ASYNCH_WF_AUDIT_MAX_RETRY"))) {
				maxRetry = Integer.parseInt(cache.getCacheDetailsFromProp("SYSCONFIG","FO_ASYNCH_WF_AUDIT_MAX_RETRY"));
				_logger.info("FO async max retry count ........" + maxRetry);
				configuredMaxRetry = maxRetry;

			}

			/*
			 * if (cache.getCacheDetailsFromProp("SYSCONFIG", "FO_AUDIT_REQD") != null) {
			 * isFOAuditRequired= Boolean.valueOf(cache.getCacheDetailsFromProp("SYSCONFIG",
			 * "FO_AUDIT_REQD") ); _logger.info("isFOAuditRequired ........"+
			 * isFOAuditRequired);
			 * 
			 * }
			 */
			// parseResponse(genericDTO,response);
			if (!(genericDTO.getStrAttributes().get("sub_order_id") != null
					&& !genericDTO.getStrAttributes().get("sub_order_id").equalsIgnoreCase(""))) {
				_logger.info("No subOrder in the request !!!");
				return;
			}
			fetchFOOrderFeature(genericDTO);

			if (genericDTO.getStrAttributes().get("action") == null
					&& genericDTO.getStrAttributes().get("order_id") == null) {

				if (genericDTO.getStrAttributes().get("action") == null
						&& genericDTO.getStrAttributes().get("order_id") == null) {
					while ((genericDTO.getStrAttributes().get("action") == null
							&& genericDTO.getStrAttributes().get("order_id") == null) && maxRetry-- > 0) {
						Thread.sleep(interval);
						fetchFOOrderFeature(genericDTO);
					}

					if (maxRetry == 0)
						_logger.info(genericDTO.getTransactionId()
								+ " | Didn't get info from WORKITEM_AUDIT_INFO even after " + (interval * maxRetry)
								+ "ms");
					maxRetry = configuredMaxRetry;
				}
			} 
			
			_logger.info("stage code reciecved " + genericDTO.getStrAttributes().get("order_code"));
			
			if (genericDTO.getStrAttributes().get("action") == null) {
				orderManagerUtility.getMainOrderInfo(genericDTO);
				if (genericDTO.getStrAttributes().get("order_info") != null) {
					_logger.info(genericDTO.getTransactionId() + " order_info : "
							+ genericDTO.getStrAttributes().get("order_info"));
					orderManagerUtility.parseMainOrderInfo(genericDTO);
				} else
					_logger.info(genericDTO.getTransactionId() + " order_info is null");
				if (genericDTO.getStrAttributes().get("action") == null || (genericDTO.getStrAttributes().get("action") != null && "CreateBatchOrder".equals(genericDTO.getStrAttributes().get("action")))) {
					WorkFlowUtil.fetchWFAuditDetails(genericDTO);
				}

			}

			if (genericDTO.getStrAttributes().get("action") != null
					&& genericDTO.getStrAttributes().get("order_id") != null) {
				WorkFlowUtil.fetchWIAuditDetails(genericDTO);
				if (genericDTO.getStrAttributes().get("process_instance_ids") == null) {
					/*
					 * int maxRetry = 4; if
					 * (CommonUtil.validateField(cache.getCacheDetailsFromProp("SYSCONFIG",
					 * "FO_ASYNCH_WF_AUDIT_MAX_RETRY"))) { maxRetry =
					 * Integer.parseInt(cache.getCacheDetailsFromProp("SYSCONFIG",
					 * "FO_ASYNCH_WF_AUDIT_MAX_RETRY")) > maxRetry ? maxRetry :
					 * Integer.parseInt(cache.getCacheDetailsFromProp("SYSCONFIG",
					 * "FO_ASYNCH_WF_AUDIT_MAX_RETRY"));
					 * _logger.info("FO async max retry count ........" + maxRetry);
					 * 
					 * }
					 */

					while ((genericDTO.getStrAttributes().get("process_instance_ids") == null) && maxRetry-- > 0) {
						Thread.sleep(interval);
						WorkFlowUtil.fetchWIAuditDetails(genericDTO);
					}
					if (maxRetry == 0)
						_logger.info(genericDTO.getTransactionId()
								+ " | Didn't get info from WORKITEM_AUDIT_INFO even after " + (interval * maxRetry)
								+ "ms");
					maxRetry = configuredMaxRetry;
				}
			}

			if (genericDTO.getStrAttributes().get("process_instance_ids") != null) {
				/*
				 * int maxRetry = 4; if
				 * (CommonUtil.validateField(cache.getCacheDetailsFromProp("SYSCONFIG",
				 * "FO_ASYNCH_WF_AUDIT_MAX_RETRY"))) { maxRetry =
				 * Integer.parseInt(cache.getCacheDetailsFromProp("SYSCONFIG",
				 * "FO_ASYNCH_WF_AUDIT_MAX_RETRY")) > maxRetry ? maxRetry :
				 * Integer.parseInt(cache.getCacheDetailsFromProp("SYSCONFIG",
				 * "FO_ASYNCH_WF_AUDIT_MAX_RETRY"));
				 * _logger.info("FO async max retry count ........" + maxRetry);
				 * 
				 * }
				 */
				checkEventStartTime = System.currentTimeMillis();
				maxRetry = configuredMaxRetry;
			//	isFOEventWaiting = _checkForFOWaitingSignal(genericDTO);
				
				while (!isFOEventWaiting && maxRetry-- > 0) {
					_logger.info(genericDTO.getTransactionId()
							+ "waiting for " + interval
							+ "ms");
					
					isFOEventWaiting = _checkForFOWaitingSignal(genericDTO);

					if(isFOEventWaiting) {
						if(checkStageflow(genericDTO)) {
							isFOEventWaiting = false;
							if(maxRetry - 1 == 0) {
								/*maxRetry = configuredMaxRetry;*/
								_logger.info("notfound " + genericDTO.getStrAttributes().get("sub_order_id"));
								
							}
						}
					}
					if (!isFOEventWaiting) {
					Thread.sleep(interval);
					WorkFlowUtil.fetchWIAuditDetails(genericDTO);
					}
					
					
				}
				maxRetry = configuredMaxRetry;
				while (!isFOEventWaiting && maxRetry-- > 0) {
					_logger.info(genericDTO.getTransactionId()
							+ "waiting for " + interval
							+ "ms");
					Thread.sleep(interval);					
					WorkFlowUtil.fetchWIAuditDetails(genericDTO);
					isFOEventWaiting = _checkForFOWaitingSignal(genericDTO);
					if(isFOEventWaiting) {
						if(checkStageflow(genericDTO)) {
							isFOEventWaiting = false;
							if(maxRetry - 1 ==0) {
								/*maxRetry = configuredMaxRetry;*/
								_logger.info("notfound "  + genericDTO.getStrAttributes().get("sub_order_id"));
								
							}
						}
					}

				}
			}
			_logger.info("Event check duration"+ (System.currentTimeMillis() - checkEventStartTime));
			_logger.info("Fo call back action :" + genericDTO.getStrAttributes().get("action") + " : sub_order_id :"
					+ genericDTO.getStrAttributes().get("sub_order_id") + " : order_id :"
					+ genericDTO.getStrAttributes().get("order_id") +
					"Order_code in FO resp" + genericDTO.getStrAttributes().get("order_code")+
					" for signal ref" + genericDTO.getStrAttributes().get("signal_ref"));
			action = genericDTO.getStrAttributes().get("action");
			
		
			
			
			
			
			
			
			if (isFOEventWaiting) {
				orderManagerUtility.getMainOrderInfo(genericDTO);
				if (genericDTO.getStrAttributes().get("order_info") != null) {
					_logger.info(genericDTO.getTransactionId() + " order_info : "
							+ genericDTO.getStrAttributes().get("order_info"));
					orderManagerUtility.parseMainOrderInfo(genericDTO);
				} else
					_logger.info(genericDTO.getTransactionId() + " order_info is null");

				genericDTO.getStrAttributes().put("action", action);
				/* WorkFlowUtil.fetchWFAuditDetails(genericDTO); */

				_logger.info(genericDTO.getTransactionId() + " action " + genericDTO.getStrAttributes().get("action")
						+ " Txn Id :" + genericDTO.getTransactionId());

				/*
				 * if (genericDTO.getStrAttributes().get("action") == null) { interval = 200; if
				 * (cache.getCacheDetailsFromProp("SYSCONFIG",
				 * "FO_ASYNCH_PROCESS_DELAY_"+get(GenericConstants.key_orderfeature)) != null) {
				 * interval= Long.valueOf(cache.getCacheDetailsFromProp("SYSCONFIG",
				 * "FO_ASYNCH_PROCESS_DELAY_"+get(GenericConstants.key_orderfeature)) );
				 * _logger.info("processing asych call back ........"+ interval);
				 * 
				 * } Thread.sleep(interval); WorkFlowUtil.fetchWFAuditDetails(genericDTO);
				 * _logger.info(genericDTO.getTransactionId() + " action " +
				 * genericDTO.getStrAttributes().get("action") + " Txn Id :"+
				 * genericDTO.getTransactionId());
				 * 
				 * }
				 */

				if (genericDTO.getStrAttributes().get("action") == null
						&& get(GenericConstants.key_orderfeature) != null
						&& "AccountAdjustment".equalsIgnoreCase(get(GenericConstants.key_orderfeature))) {
					if (genericDTO.getStrAttributes().get("error_code") != null
							&& "9".equalsIgnoreCase(genericDTO.getStrAttributes().get("error_code"))) {
						updateSiganlSubOrderStage(genericDTO, "FO_ACC_ADD",
								WorkflowConstants.str_sub_order_stage_status_completed);
					} else {
						updateSiganlSubOrderStage(genericDTO, "FO_ACC_ADD",
								WorkflowConstants.str_sub_order_stage_status_failed);
					}
					genericDTO = getStoredGenericDTO(genericDTO);
					OrderManagerUtility.updateSubOrderStages(genericDTO);
					OrderManagerUtility.completeSubOrder(genericDTO);
					OrderManagerUtility.completeMainOrder(genericDTO);

					genericDTO.setFlowContinue(false);
				}

				
				if (genericDTO.isFlowContinue()) {
					put("flowCallBack", "success");
					put("feature", "update");
					put("payment_call_back",null);
					genericDTO.setCmdName(GenericConstants.command_sync_persistence);
					genericDTO.setAdapType(1);
					syncProcess.serviceRequestProcessing(genericDTO);
				} else {
					_logger.info(new StringBuilder().append(logFormat).append("Flow stop set").toString() + " Txn Id :"
							+ genericDTO.getTransactionId());
				}
			} else {
				_logger.info(
						"No signal available for subOrderId :" + genericDTO.getStrAttributes().get("sub_order_id"));
			}

		} catch (Exception e) {
			_logger.info(" | Exception occured in parseLmsViewAccountResponse :- " + e, e);
		} finally {
			syncProcess = null;
			orderManagerUtility = null;
		}
	}
	private boolean checkStageflow(GenericDTO genericDTO) {
		// TODO Auto-generated method stub


		NGTableDataDTO workitemStageMapping = null;
		String orderCode = null;
		String signalRef = null;
		String signalStage = null;
		String workItemHandler = null;
		boolean stagecomplete = false;
		String stageCode = null;
		GetDataFromCache cache = null;

		try {
			cache = new  GetDataFromCache();

			
				orderCode = genericDTO.getStrAttributes().get("order_code");
				signalRef = genericDTO.getStrAttributes().get("signalRef");

				workitemStageMapping = cache.getCacheDetailsFromDBMap("WORKITEM_STAGE_MAPPING_1",
						orderCode + "_" + signalRef);
				if (workitemStageMapping != null) {
					signalStage = workitemStageMapping.getNgTableData().get("STAGE_CODE");
					workItemHandler = workitemStageMapping.getNgTableData().get("WORKITEM_HANDLERS");

				} else
					_logger.info(" | " + genericDTO.getTransactionId() + " | workitemStageMapping is null");

				_logger.info("workItem stage mapping got" + workItemHandler + "stage code" + signalStage);

				stagecomplete = checkStageCompleted(genericDTO, workItemHandler, signalStage);

				_logger.info("returning " + stagecomplete);

				

			

		} catch (Exception e) {
			// TODO: handle exception
			_logger.info("exception in checkstageflow" + e,e);
		}

	
		
		return stagecomplete;
		
		
	}

	private boolean checkStageCompleted(GenericDTO genericDTO, String workItemHandler, String signalStage) {
		// TODO Auto-generated method stub


		Transaction txn = null;
		Session session = null;
		String hql = null;
		Query query = null;
		String orderStage = null;
		
		try {

			session = HibernateUtils.openSession();
			// conn = ConnectionPool.getConnection(0);

			/*txn = session.beginTransaction();*/

			hql = "select subOrderStageStatus from SubOrderStagesConfig where subOrderId = :subOrderId "
					+ "and orderId = :orderId and stageCode = :stageCode  ";
			query = session.createQuery(hql);
			_logger.info("executed " + query);
			query.setString("subOrderId", get(GenericConstants.key_sub_order_id));
			query.setString("orderId", get(GenericConstants.key_order_id));
			query.setString("stageCode",signalStage);

			List<String> subDetails = (List<String>) query.list();
			_logger.info("executed " + subDetails.size());
			for (String subDetail : subDetails) {
				orderStage = (String) subDetail;
			
			}

			/*
			 * Iterator it = query.iterate();
			 * 
			 * // query.get
			 * 
			 * while (it.hasNext()) { orderFeature = (String) it.next(); orderId =
			 * _logger.info(genericDTO.getTransactionId() + " orderFeature " +
			 * orderFeature); }
			 */

			/*try {
				// if (!txn.isActive())
				txn.commit();
			} catch (Exception e) {
				_logger.error(genericDTO.getTransactionId() + " Exception occured in commiting the transaction "
						+ e.getMessage());
			}*/

			if(orderStage != null) {
				_logger.info("orderStage after fo " + orderStage + "sub_order_id" + genericDTO.getStrAttributes().get("sub_order_id"));
				if(orderStage.equalsIgnoreCase("Completed")) {
				return true;
				}
			}
			else {
				_logger.info("orderfeature is null" );
			}
		
			
			
			
		} catch (Exception e) {
			// if (txn != null)
			// txn.rollback();
			_logger.error(genericDTO.getTransactionId() + " | Exception occured in checkStageCompleted() :- " + e, e);

		} finally {
			if (txn != null)
				txn = null;
			HibernateUtils.closeSession(session);

			txn = null;
			session = null;
			hql = null;
			query = null;
			
		}

	
		return false;
	}

	/*public void processCallBackResponse(Response response, GenericDTO genericDTO) {

		SyncProcessBO syncProcess = null;
		String xmlStrRecieve = null;
		OrderManagerUtility orderManagerUtility = null;
		OrderEventAuditUtil orderEventAuditUtil = null;
		CDRWritingBO cdrWritingBO = null;
		GetDataFromCache cache = null;
		long interval = 10000;
		boolean isFOAuditRequired = true;
		try {
			cache = new GetDataFromCache();
			syncProcess = new SyncProcessBO();
			orderManagerUtility = new OrderManagerUtility(genericDTO);

			// xmlStrRecieve = jaxbObjectToXML(response);

			if (cache.getCacheDetailsFromProp("SYSCONFIG", "FO_ASYNCH_PROCESS_DELAY") != null) {
				interval = Long.valueOf(cache.getCacheDetailsFromProp("SYSCONFIG", "FO_ASYNCH_PROCESS_DELAY"));
				_logger.info("processing asych call back ........" + interval);
				Thread.sleep(interval);
			}
			if (cache.getCacheDetailsFromProp("SYSCONFIG", "FO_AUDIT_REQD") != null) {
				isFOAuditRequired = Boolean.valueOf(cache.getCacheDetailsFromProp("SYSCONFIG", "FO_AUDIT_REQD"));
				_logger.info("isFOAuditRequired ........" + isFOAuditRequired);

			}
			if (!(genericDTO.getStrAttributes().get("sub_order_id") != null && !genericDTO.getStrAttributes()
					.get("sub_order_id").equalsIgnoreCase(""))) {
				_logger.info("No subOrder in the request !!!");
				return;
			}
			fetchFOOrderFeature(genericDTO);
			_logger.info("order id :" + genericDTO.getStrAttributes().get("order_id") + " : sub_order_id"
					+ genericDTO.getStrAttributes().get("sub_order_id"));
			orderManagerUtility.getMainOrderInfo(genericDTO);
			if (genericDTO.getStrAttributes().get("order_info") != null) {
				_logger.info(genericDTO.getTransactionId() + " order_info : "
						+ genericDTO.getStrAttributes().get("order_info"));
				orderManagerUtility.parseMainOrderInfo(genericDTO);
			} else
				_logger.info(genericDTO.getTransactionId() + " order_info is null");

			WorkFlowUtil.fetchWFAuditDetails(genericDTO);

			_logger.info(genericDTO.getTransactionId() + " action " + genericDTO.getStrAttributes().get("action")
					+ " Txn Id :" + genericDTO.getTransactionId());

			if (genericDTO.getStrAttributes().get("action") == null) {
				interval = 200;
				if (cache.getCacheDetailsFromProp("SYSCONFIG", "FO_ASYNCH_PROCESS_DELAY_"
						+ get(GenericConstants.key_orderfeature)) != null) {
					interval = Long.valueOf(cache.getCacheDetailsFromProp("SYSCONFIG", "FO_ASYNCH_PROCESS_DELAY_"
							+ get(GenericConstants.key_orderfeature)));
					_logger.info("processing asych call back ........" + interval);

				}
				Thread.sleep(interval);
				WorkFlowUtil.fetchWFAuditDetails(genericDTO);
				_logger.info(genericDTO.getTransactionId() + " action " + genericDTO.getStrAttributes().get("action")
						+ " Txn Id :" + genericDTO.getTransactionId());

			}

			if (genericDTO.getStrAttributes().get("action") == null && get(GenericConstants.key_orderfeature) != null
					&& "AccountAdjustment".equalsIgnoreCase(get(GenericConstants.key_orderfeature))) {
				if (genericDTO.getStrAttributes().get("error_code") != null
						&& "9".equalsIgnoreCase(genericDTO.getStrAttributes().get("error_code"))) {
					updateSiganlSubOrderStage(genericDTO, "FO_ACC_ADD",
							WorkflowConstants.str_sub_order_stage_status_completed);
				} else {
					updateSiganlSubOrderStage(genericDTO, "FO_ACC_ADD",
							WorkflowConstants.str_sub_order_stage_status_failed);
				}
				genericDTO = getStoredGenericDTO(genericDTO);
				OrderManagerUtility.updateSubOrderStages(genericDTO);
				OrderManagerUtility.completeSubOrder(genericDTO);
				OrderManagerUtility.completeMainOrder(genericDTO);

				genericDTO.setFlowContinue(false);
			}

			if (isFOAuditRequired && genericDTO.getStrAttributes().get("action") == null) {

				try {
					FOCallBakProcessThread foCallBackProcess = new FOCallBakProcessThread(genericDTO);
					Thread foThread = new Thread(foCallBackProcess);
					foThread.start();
					return;
				} catch (Exception e) {
					System.exit(1);
				} finally {
					_logger.info("Thread stopped!!!!");
				}
				return;

			}
			if (genericDTO.isFlowContinue()) {
				put("flowCallBack", "success");
				put("feature", "update");
				genericDTO.setCmdName(GenericConstants.command_sync_persistence);
				genericDTO.setAdapType(1);
				syncProcess.serviceRequestProcessing(genericDTO);
			} else {
				_logger.info(new StringBuilder().append(logFormat).append("Flow stop set").toString() + " Txn Id :"
						+ genericDTO.getTransactionId());
			}

		} catch (Exception e) {
			_logger.info(" | Exception occured in parseLmsViewAccountResponse :- " + e, e);
		} finally {
			
			 * if (cache.getCacheDetailsFromProp("CDR_PROPERTIES",
			 * "Cdr_Reqd_Action") != null) { _logger.info("Cdr_Reqd_Action :"+
			 * cache.getCacheDetailsFromProp("CDR_PROPERTIES",
			 * "Cdr_Reqd_Action") + ": Txn Id :"+
			 * genericDTO.getTransactionId()); cdrWritingBO = new
			 * CDRWritingBO(); cdrWritingBO.putCdrUtility(genericDTO); }
			 

			
			 * if
			 * (cache.getCacheDetailsFromProp("SYSCONFIG","EventAuditRequired")
			 * != null && Boolean
			 * .parseBoolean(cache.getCacheDetailsFromProp("SYSCONFIG"
			 * ,"EventAuditRequired"))) { orderEventAuditUtil = new
			 * OrderEventAuditUtil();
			 * orderEventAuditUtil.auditLifeCycleEventPush(genericDTO,
			 * xmlStrRecieve, "0", "FO Callback"); }
			 

			syncProcess = null;
			orderManagerUtility = null;
			cdrWritingBO = null;
			cache = null;
		}
	}*/

	public String jaxbObjectToXML(Response response) {
		String xmlContent = null;
		try {
			// Create JAXB Context
			JAXBContext jaxbContext = JAXBContext.newInstance(Response.class);

			// Create Marshaller
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// Required formatting??
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Print XML String to Console
			StringWriter sw = new StringWriter();

			// Write XML to StringWriter
			jaxbMarshaller.marshal(response, sw);

			// Verify XML Content
			xmlContent = sw.toString();
			_logger.info(" xmlContent response recieved is :-:" + xmlContent);

		} catch (Exception e) {
			_logger.error("Exception occured in conevrting object to xml ", e);
		}
		return xmlContent;
	}

	public void parseSoapRequest(String response, GenericDTO genericDTO, String requestURI, HttpServletResponse res) {

		String statusCode = null;
		String xmlStrRecieve = null;
		String statusDesc = null;
		OrderEventAuditUtil orderEventAuditUtil = null;
		SyncProcessBO syncProcess = null;
		OrderManagerUtility orderManagerUtility = null;
		GetDataFromCache cache = null;
		long interval = 30;
		boolean isFOAuditRequired = false;
		try {
			syncProcess = new SyncProcessBO();
			orderManagerUtility = new OrderManagerUtility(genericDTO);
			cache = new GetDataFromCache();
			// xmlStrRecieve = jaxbObjectToXML(response);

			if (cache.getCacheDetailsFromProp("SYSCONFIG", "FO_ASYNCH_PROCESS_DELAY") != null) {
				interval = Long.valueOf(cache.getCacheDetailsFromProp("SYSCONFIG", "FO_ASYNCH_PROCESS_DELAY"));
				_logger.info("processing asych call back ........" + interval);
				Thread.sleep(interval);
			}
			if (cache.getCacheDetailsFromProp("SYSCONFIG", "FO_AUDIT_REQD") != null) {
				isFOAuditRequired = Boolean.valueOf(cache.getCacheDetailsFromProp("SYSCONFIG", "FO_AUDIT_REQD"));
				_logger.info("isFOAuditRequired ........" + isFOAuditRequired);

			}
			// parseResponse(genericDTO,response);

			if (!(genericDTO.getStrAttributes().get("sub_order_id") != null && !genericDTO.getStrAttributes()
					.get("sub_order_id").equalsIgnoreCase(""))) {
				_logger.info("No subOrder in the request !!!");
				return;
			}
			fetchFOOrderFeature(genericDTO);
			_logger.info("Orderfeature");
			orderManagerUtility.getMainOrderInfo(genericDTO);
			if (genericDTO.getStrAttributes().get("order_info") != null) {
				_logger.info(genericDTO.getTransactionId() + " order_info : "
						+ genericDTO.getStrAttributes().get("order_info"));
				orderManagerUtility.parseMainOrderInfo(genericDTO);
			} else
				_logger.info(genericDTO.getTransactionId() + " order_info is null");

			WorkFlowUtil.fetchWFAuditDetails(genericDTO);

			_logger.info(genericDTO.getTransactionId() + " action " + genericDTO.getStrAttributes().get("action")
					+ " Txn Id :" + genericDTO.getTransactionId());

			if (genericDTO.getStrAttributes().get("action") == null) {
				interval = 200;
				if (cache.getCacheDetailsFromProp("SYSCONFIG", "FO_ASYNCH_PROCESS_DELAY_"
						+ get(GenericConstants.key_orderfeature)) != null) {
					interval = Long.valueOf(cache.getCacheDetailsFromProp("SYSCONFIG", "FO_ASYNCH_PROCESS_DELAY_"
							+ get(GenericConstants.key_orderfeature)));
					_logger.info("processing asych call back ........" + interval);

				}
				Thread.sleep(interval);
				WorkFlowUtil.fetchWFAuditDetails(genericDTO);
				_logger.info(genericDTO.getTransactionId() + " action " + genericDTO.getStrAttributes().get("action")
						+ " Txn Id :" + genericDTO.getTransactionId());

			}

			if (genericDTO.getStrAttributes().get("action") == null && get(GenericConstants.key_orderfeature) != null
					&& "AccountAdjustment".equalsIgnoreCase(get(GenericConstants.key_orderfeature))) {
				if (genericDTO.getStrAttributes().get("error_code") != null
						&& "9".equalsIgnoreCase(genericDTO.getStrAttributes().get("error_code"))) {
					updateSiganlSubOrderStage(genericDTO, "FO_ACC_ADD",
							WorkflowConstants.str_sub_order_stage_status_completed);
				} else {
					updateSiganlSubOrderStage(genericDTO, "FO_ACC_ADD",
							WorkflowConstants.str_sub_order_stage_status_failed);
				}
				genericDTO = getStoredGenericDTO(genericDTO);
				OrderManagerUtility.updateSubOrderStages(genericDTO);
				OrderManagerUtility.completeSubOrder(genericDTO);
				OrderManagerUtility.completeMainOrder(genericDTO);

				genericDTO.setFlowContinue(false);
			}

			if (isFOAuditRequired && genericDTO.getStrAttributes().get("action") == null) {

				try {
					FOCallBakProcessThread foCallBackProcess = new FOCallBakProcessThread(genericDTO);
					Thread foThread = new Thread(foCallBackProcess);
					foThread.start();
					return;
				} catch (Exception e) {
					System.exit(1);
				} finally {
					_logger.info("Thread stopped!!!!");
				}
				return;

			}
			if (genericDTO.isFlowContinue()) {
				put("flowCallBack", "success");
				put("feature", "update");
				genericDTO.setCmdName(GenericConstants.command_sync_persistence);
				genericDTO.setAdapType(1);
				syncProcess.serviceRequestProcessing(genericDTO);
			} else {
				_logger.info(new StringBuilder().append(logFormat).append("Flow stop set").toString() + " Txn Id :"
						+ genericDTO.getTransactionId());
			}
			
			_logger.info("process_instance_ids after call back" + genericDTO.getStrAttributes().get("process_instance_ids"));

		} catch (Exception e) {
			_logger.info(" | Exception occured in parseLmsViewAccountResponse :- " + e, e);
		} finally {

			if (cache.getCacheDetailsFromProp("SYSCONFIG", "EventAuditRequired") != null
					&& Boolean.parseBoolean(cache.getCacheDetailsFromProp("SYSCONFIG", "EventAuditRequired"))) {
				orderEventAuditUtil = new OrderEventAuditUtil();
				orderEventAuditUtil.auditLifeCycleEventPush(genericDTO, response, "0", "FO Callback");
			}

			statusCode = null;
			statusDesc = null;
			syncProcess = null;
			orderManagerUtility = null;
		}
	}

	public static void main(String[] args) {
		FlowOneUtility f = new FlowOneUtility();
		String xmlStrRecieve = "<?xml version=\"1.0\" ?><S:Envelope xmlns:S=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\"><env:Header/><S:Body><Response xmlns=\"http://soa.comptel.com/2011/02/instantlink\"><ResponseHeader><RequestId>21340</RequestId><Status>0</Status><OrderNo>1-3P1DWH_-20181012011526</OrderNo><StatusMessage>InstantLink accepted request with request id: 21340 for order no: 1-3P1DWH_-20181012011526</StatusMessage> </ResponseHeader><ResponseParameters/></Response></S:Body></S:Envelope>";
		System.out.println("receiver xml " + xmlStrRecieve);
		f.parseResponse(null, xmlStrRecieve);
	}

	/*public void fetchFOOrderFeature(GenericDTO genericDTO) {

		Transaction txn = null;
		Session session = null;
		String hql = null;
		Query query = null;
		String orderFeature = null;
		String orderId = null;
		
		try {

			session = HibernateUtils.openSession();
			// conn = ConnectionPool.getConnection(0);

			txn = session.beginTransaction();

			hql = "select subOrderName,orderId from SubOrderDetailsConfig where subOrderId = :subOrderId";
			query = session.createQuery(hql);

			query.setString("subOrderId", get(GenericConstants.key_sub_order_id));

			List<Object[]> subDetails = (List<Object[]>) query.list();
			for (Object[] subDetail : subDetails) {
				orderFeature = (String) subDetail[0];
				orderId = (String) subDetail[1];

			}

			
			 * Iterator it = query.iterate();
			 * 
			 * // query.get
			 * 
			 * while (it.hasNext()) { orderFeature = (String) it.next(); orderId
			 * = _logger.info(genericDTO.getTransactionId() + " orderFeature " +
			 * orderFeature); }
			 

			try {
				// if (!txn.isActive())
				txn.commit();
			} catch (Exception e) {
				_logger.error(genericDTO.getTransactionId() + " Exception occured in commiting the transaction "
						+ e.getMessage());
			}

			if (nullCheck(orderFeature)) {
				put(GenericConstants.key_orderfeature, orderFeature);
				// put("CallBackReqType", orderFeature);
			}

			if (nullCheck(orderId)) {
				put(GenericConstants.key_order_id, orderId);
				// put("CallBackReqType", orderFeature);
			}

			_logger.info("FO call back with sub_order_id :" + get(GenericConstants.key_sub_order_id) + " , order_id :"
					+ get(GenericConstants.key_order_id) + ", order_feature :" + get(GenericConstants.key_orderfeature));

		} catch (Exception e) {
			if (txn != null)
				txn.rollback();
			_logger.error(genericDTO.getTransactionId() + " | Exception occured in updateQuotaMNP() :- " + e, e);

		} finally {
			if (txn != null)
				txn = null;
			HibernateUtils.closeSession(session);

			txn = null;
			session = null;
			hql = null;
			query = null;
			orderFeature = null;
			orderId = null;
		}

	}
*/
	
	public void fetchFOOrderFeature(GenericDTO genericDTO) {

		Transaction txn = null;
		Session session = null;
		String hql = null;
		Query query = null;
		String orderFeature = null;
		String orderId = null;
		String action =  null;
		String orderCode = null;
		try {

			session = HibernateUtils.openSession();
			// conn = ConnectionPool.getConnection(0);

			/*txn = session.beginTransaction();*/

			hql = "select subOrderName,orderId,action,orderCode from SubOrderDetailsConfig where subOrderId = :subOrderId";
			query = session.createQuery(hql);

			query.setString("subOrderId", get(GenericConstants.key_sub_order_id));

			List<Object[]> subDetails = (List<Object[]>) query.list();
			for (Object[] subDetail : subDetails) {
				orderFeature = (String) subDetail[0];
				orderId = (String) subDetail[1];
				action = (String) subDetail[2];
				orderCode = (String)subDetail[3];

			}

			/*
			 * Iterator it = query.iterate();
			 * 
			 * // query.get
			 * 
			 * while (it.hasNext()) { orderFeature = (String) it.next(); orderId =
			 * _logger.info(genericDTO.getTransactionId() + " orderFeature " +
			 * orderFeature); }
			 */

			/*try {
				// if (!txn.isActive())
				txn.commit();
			} catch (Exception e) {
				_logger.error(genericDTO.getTransactionId() + " Exception occured in commiting the transaction "
						+ e.getMessage());
			}*/

			if (nullCheck(orderFeature)) {
				put(GenericConstants.key_orderfeature, orderFeature);
				// put("CallBackReqType", orderFeature);
			}

			if (nullCheck(orderId)) {
				put(GenericConstants.key_order_id, orderId);
				// put("CallBackReqType", orderFeature);
			}
			

			if (nullCheck(orderCode)) {
				put("order_code", orderCode);
				// put("CallBackReqType", orderFeature);
			}

			if (nullCheck(action)) {
				put("action", action);
				// put("CallBackReqType", orderFeature);
			}
			_logger.info("FO call back with sub_order_id :" + get(GenericConstants.key_sub_order_id) + " , order_id :"
					+ get(GenericConstants.key_order_id) + ", order_feature :"
					+ get(GenericConstants.key_orderfeature)
					+ " : action :"+ get("action")
					+ "order_code FO " + get("order_code"));

		} catch (Exception e) {
			// if (txn != null)
			// txn.rollback();
			_logger.error(genericDTO.getTransactionId() + " | Exception occured in updateQuotaMNP() :- " + e, e);

		} finally {
			if (txn != null)
				txn = null;
			HibernateUtils.closeSession(session);

			txn = null;
			session = null;
			hql = null;
			query = null;
			orderFeature = null;
			orderId = null;
		}

	}
	public void parseResponse(GenericDTO genericDTO, String xmlStrRecieve) {
		String statusCode = null;
		String statusDesc = null;
		SAXHandlerWorkFlow contentHandler = null;
		InputStream inp = null;
		XMLReader parser = null;
		InputSource is = null;

		HashMap<String, String> validationResult = new HashMap<String, String>();
		HashMap<String, String> respMap = null;

		try {

			if (xmlStrRecieve != null) {
				inp = new ByteArrayInputStream(xmlStrRecieve.getBytes());

				parser = org.xml.sax.helpers.XMLReaderFactory.createXMLReader();
				contentHandler = new SAXHandlerWorkFlow();
				parser.setContentHandler(contentHandler);
				is = new InputSource(inp);
				contentHandler.setResponeMap(validationResult);
				parser.parse(is);
				respMap = contentHandler.getResponeMap();
				// logger.info(respMap);
				if (respMap != null) {

					_logger.info("respMap :::" + respMap.entrySet() + " :: Status = " + respMap.get("statusCode"));

					_logger.info("" + respMap.get("OrderNo"));
					_logger.info("" + respMap.get("RequestId"));

					put("statusCode", respMap.get("statusCode"));
					put("error_code", respMap.get("statusCode"));
					put("error_desc", respMap.get("StatusMessage"));
					put("sub_order_id", respMap.get("OrderNo"));
					put("fo_request_id", respMap.get("RequestId"));
				}

			}

		} catch (Exception e) {
			_logger.info(" | Exception occured in parseLmsViewAccountResponse :- " + e, e);
		} finally {
			statusCode = null;
			statusDesc = null;
			contentHandler = null;
			inp = null;
			parser = null;
			is = null;

			validationResult = null;
			respMap = null;

		}

	}

	public String getMNPRequest(String action) {

		String request = null;
		String xml = null;
		String keys = null;
		GetDataFromCache cache = null;

		try {
			cache = new GetDataFromCache();

			if (cache.getCacheDetailsFromProp(PROPFILE, "REQ_DATA_" + action) != null)
				xml = cache.getCacheDetailsFromProp(PROPFILE, "REQ_DATA_" + action);
			if (cache.getCacheDetailsFromProp(PROPFILE, "REQ_KEYS_" + action) != null)
				keys = cache.getCacheDetailsFromProp(PROPFILE, "REQ_KEYS_" + action);

			if (xml != null && keys != null)
				request = buildRequest(xml, keys);
			else
				throw new CommonException("Request or Keys configured is null");

		} catch (CommonException e) {
			_logger.warn(e.getMessage());
		} catch (Exception e) {
			_logger.error("Exception occured " + e, e);
		}
		return request;
	}

	public String processRequest(GenericDTO genericDTO, String request, String action, boolean isRevert) {

		String response = null;
		GetDataFromCache cache = null;

		try {
			cache = new GetDataFromCache();

			switch (action) {

			case "UPDATE_ORDER":
			case "TERMINATION":
				setCpAttributes("WF");
				break;
			case "SEND_NOTIFICATION":
				setCpAttributes("NG");
				break;
			default:
				break;
			}

			if (request != null && !request.isEmpty()) {
				if (genericDTO.isFlowContinue()) {
					response = callCP(request, action, genericDTO);
					if ("UPDATE_ORDER".equalsIgnoreCase(action)) {
						setCpAttributes("WF");
						/*
						 * if (cache.getCacheDetailsFromProp("CP_CONFIGS",
						 * "WF_RESP_STATUS_PATH") != null)
						 * genericDTO.getStrAttributes().put(WorkflowConstants.
						 * key_resp_status_path,
						 * cache.getCacheDetailsFromProp("CP_CONFIGS",
						 * "WF_RESP_STATUS_PATH").trim()); if
						 * (cache.getCacheDetailsFromProp("CP_CONFIGS",
						 * "MNP_RESP_STATUS_DESC_PATH") != null)
						 * genericDTO.getStrAttributes().put(WorkflowConstants.
						 * key_resp_status_desc_path,
						 * cache.getCacheDetailsFromProp("CP_CONFIGS",
						 * "MNP_RESP_STATUS_DESC_PATH").trim());
						 */
					}
					if (genericDTO.isFlowContinue() && validateStatus(response)) {
						setSuccess();
					} else {
						put("error_system", cache.getCacheDetailsFromProp(PROPFILE, "MNP"));
						put(GenericConstants.key_life_cycle_stage, "Failed");
						put(GenericConstants.key_failed_order_reason, "MNP");
					}
				}
			} else
				throw new CommonException("Request to be sent to SurePay is null... so not calling SurePay");
		} catch (CommonException e) {
			_logger.warn(logFormat + e.getMessage());
			setError();
		} catch (Exception e) {
			_logger.error(genericDTO.getTransactionId() + " | Exception occured : " + e.getMessage());
		}
		return response;
	}

	// this method will update the status of a stage related to a signal
	private void updateSiganlSubOrderStage(GenericDTO genericDTO, String stageCode, String stageStaus) {

		Session session = null;
		String orderId = null;
		String hql = null;
		Query query = null;
		Transaction txn = null;
		String subOrderId = null;
		String errorCode = null;
		String errorDesc = null;

		try {
			session = HibernateUtils.openSession();
			orderId = genericDTO.getStrAttributes().get("order_id");
			subOrderId = genericDTO.getStrAttributes().get("sub_order_id");
			errorCode = genericDTO.getStrAttributes().get("error_code");
			// if (errorDesc == null)
			errorDesc = genericDTO.getStrAttributes().get("error_desc");
			hql = "update SubOrderStagesConfig set subOrderStageStatus=:subOrderStageStatus,error_code=:errorCode,error_desc=:errorDesc where stageCode=:stageCode "
					+ "and orderId=:orderId and subOrderId=:subOrderId";
			query = session.createQuery(hql);
			query.setString("orderId", orderId);
			query.setString("subOrderStageStatus", stageStaus);
			query.setString("stageCode", stageCode);
			query.setString("subOrderId", subOrderId);
			query.setString("errorCode", errorCode);
			query.setString("errorDesc", errorDesc);

			txn = session.beginTransaction();
			query.executeUpdate();
			try {
				txn.commit();
			} catch (Exception e) {
				_logger.error(genericDTO.getTransactionId() + " | unable to commit transaction " + e.getMessage());
			}

		} catch (Exception e) {
			_logger.info(genericDTO.getTransactionId() + " | Exception occured in updatePaymentSubOrderStage() " + e, e);
		} finally {
			HibernateUtils.closeSession(session);

			session = null;
			orderId = null;
			hql = null;
			query = null;
			txn = null;
			subOrderId = null;
			errorCode = null;
			errorDesc = null;
		}

	}

	private GenericDTO getStoredGenericDTO(GenericDTO genericDTO) {

		java.sql.Blob genericDTOBlob = null;
		LinkedHashMap<String, Object> attrMap = null;
		LinkedHashMap<String, String> strAttrMap = null;
		String oldTransactionId = null;

		try {

			_logger.info(genericDTO.getTransactionId() + " |  Merging persisted Generic DTO and local Generic dTO");

			_logger.info(genericDTO.getTransactionId() + " | feature before merging genericDTO | "
					+ genericDTO.getStrAttributes().get(GenericConstants.key_feature));

			if (genericDTO.getAttributes().get("genericDTOBlob") != null) {

				attrMap = genericDTO.getAttributes();
				strAttrMap = genericDTO.getStrAttributes();
				oldTransactionId = genericDTO.getTransactionId();

				genericDTOBlob = (java.sql.Blob) genericDTO.getAttributes().get("genericDTOBlob");
				byte[] bdata = genericDTOBlob.getBytes(1, (int) genericDTOBlob.length());
				ByteArrayInputStream baip = new ByteArrayInputStream(bdata);
				ObjectInputStream ois = new ObjectInputStream(baip);
				genericDTO = (GenericDTO) ois.readObject();
				genericDTO.setTransactionId(oldTransactionId);
				genericDTO.getStrAttributes().putAll(strAttrMap);
				genericDTO.getAttributes().putAll(attrMap);
				_logger.info(genericDTO.getTransactionId() + " | feature after merging genericDTO | "
						+ genericDTO.getStrAttributes().get(GenericConstants.key_feature));

			}

			_logger.info(genericDTO.getTransactionId() + " | genericDTO.isFlowContinue " + genericDTO.isFlowContinue());
			genericDTO.setFlowContinue(true);
			genericDTO.setException(false);
			if (genericDTO.getStrAttributes().containsKey("type"))
				genericDTO.getStrAttributes().remove("type");
			if (genericDTO.getStrAttributes().containsKey("sim_delivery"))
				genericDTO.getStrAttributes().remove("sim_delivery");
			if (genericDTO.getStrAttributes().containsKey("hlr_provisioning"))
				genericDTO.getStrAttributes().remove("hlr_provisioning");

			/*
			 * _logger.info(genericDTO.getTransactionId() +
			 * " | Str attributes after merging GenericDTO " +
			 * genericDTO.getStrAttributes().entrySet());
			 */

		} catch (Exception e) {
			_logger.info(genericDTO.getTransactionId() + " | Exception occured in getStoredGenericDTO() " + e, e);
		} finally {
			genericDTOBlob = null;
			attrMap = null;
			strAttrMap = null;
			oldTransactionId = null;
		}

		return genericDTO;

	}

	private void fetchSubOrderWorkFlowAudit(GenericDTO genericDTO) {

		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<WorkItemInfo> workItemInfos = null;
		WorkItemInfo workItemInfo = null;
		String orderId = null;
		String subOrderId = null;
		String processId = null;
		String workItem = null;
		String skipRequired = null;
		String feature = null;
		int seqId = 0;
		String processInstanceId = null;
		StringBuilder processInstanceIds = null;

		try {

			workItemInfos = new ArrayList<>();
			processInstanceIds = new StringBuilder();
			if (genericDTO.getStrAttributes().get(GenericConstants.key_order_id) != null)
				orderId = genericDTO.getStrAttributes().get(GenericConstants.key_order_id);

			if (genericDTO.getStrAttributes().get(GenericConstants.key_sub_order_id) != null)
				subOrderId = genericDTO.getStrAttributes().get(GenericConstants.key_sub_order_id);

			conn = ConnectionPool.getConnection(0);

			String selectSql = "select wi.work_item_name, wf.action, wf.sub_order_name, wi.skip_required, wi.seq_id, wi.process_instance_id "
					+ "from SUB_ORDER_DETAILS wf join WORKITEM_AUDIT_INFO wi on wf.order_id = wi.order_id "
					+ "where wf.order_id = ? and wi.sub_order_id = ? ";

			pstmt = conn.prepareStatement(selectSql);
			pstmt.setString(1, orderId);
			pstmt.setString(2, subOrderId);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				workItemInfo = new WorkItemInfo();
				workItem = rs.getString(1);
				processId = rs.getString(2);
				feature = rs.getString(3);
				skipRequired = rs.getString(4);
				seqId = rs.getInt(5);
				processInstanceId = rs.getString(6);
				processInstanceIds.append(processInstanceId).append(",");

				if (workItem != null && skipRequired != null) {
					// _logger.info(workItem+" "+skipRequired);
					genericDTO.getStrAttributes().put(workItem, skipRequired);
				}
				workItemInfo = new WorkItemInfo();
				workItemInfo.setSeqId(seqId);
				workItemInfo.setWorkItemName(workItem);
				workItemInfo.setProcessInstanceId(processInstanceId);
				workItemInfos.add(workItemInfo);

			}

			// _logger.info(genericDTO.getTransactionId() +
			// " workItemInfos size : " +
			// workItemInfos.size());

			if (!processInstanceIds.toString().isEmpty()) {

				if (processInstanceIds.toString().endsWith(","))
					processInstanceIds = processInstanceIds.deleteCharAt(processInstanceIds.length() - 1);
				genericDTO.getStrAttributes().put("process_instance_ids", processInstanceIds.toString());
			}

			if (workItemInfos != null && !workItemInfos.isEmpty()) {
				genericDTO.getAttributes().put("workItemInfos", workItemInfos);
			}

			// _logger.info(genericDTO.getTransactionId() + " processId " +
			// processId);

			if (processId != null)
				genericDTO.getStrAttributes().put(GenericConstants.key_action, processId);

			if (feature != null)
				genericDTO.getStrAttributes().put(GenericConstants.key_orderfeature, feature);

			if (seqId != 0)
				genericDTO.getStrAttributes().put("WF_SEQ_ID", seqId + "");

			// _logger.info("WIH " + workItems.toString());
			_logger.info(" | " + genericDTO.getTransactionId() + " | SQL :: " + selectSql);

		} catch (Exception e) {
			_logger.error("Exception Occured at = " + e, e);
		} finally {

			try {
				if (rs != null)
					rs.close();
			} catch (Exception ee) {
				_logger.error("Exception occured in ", ee);
			}

			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception ee) {
				_logger.error("Exception occured in ", ee);
			}

			try {
				if (conn != null) {
					conn.close();
					_logger.info(genericDTO.getTransactionId() + " connection closed");
				}
			} catch (Exception ee) {
				_logger.error("Exception occured in ", ee);
			}

			rs = null;
			pstmt = null;
			conn = null;
		}
	}
	
	private boolean _checkForFOWaitingSignal(GenericDTO genericDTO) {
		boolean isFOWaiting = false;
		try {
			isFOWaiting = checkPendingFOSingnal(genericDTO);
		} catch (Exception e) {
			logger.info(" | Exception occured in checkForFOWaitingSignal :- " + e, e);
		} finally {
			_logger.info("isFOWaiting :" + isFOWaiting);
		}
		return isFOWaiting;
	}
	
	private boolean checkPendingFOSingnal(GenericDTO genericDTO) {
		String processInstanceIds = null;
		String processInstanceId = null;
		Session session = null;
		String hql = null;
		Query query = null;
		Criteria criteria = null;
		List<EventTypesConfig> eventTypes = null;
		boolean isFOWaiting = false;

		try {

			_logger.info(genericDTO.getTransactionId() + " | checking process with active event");

			processInstanceIds = genericDTO.getStrAttributes().get("process_instance_ids");

			_logger.info(genericDTO.getTransactionId() + " | process instances created " + processInstanceIds);

			if (CommonUtil.validateField(processInstanceIds)) {

				List<Integer> pidList = new ArrayList<>();

				String[] pidString = processInstanceIds.split(",");

				for (int i = 0; i < pidString.length; i++) {
					pidList.add(Integer.parseInt(pidString[i]));
				}

				session = HibernateUtils.openSession();
				criteria = session.createCriteria(EventTypesConfig.class);
				criteria.add(Restrictions.in("instanceId", pidList));
				eventTypes = criteria.list();

				if (eventTypes != null) {
					for (EventTypesConfig eventType : eventTypes) {
						_logger.info(genericDTO.getTransactionId() + " processInstanceId " + eventType.getInstanceId()
								+ " element " + eventType.getEventTypes());
						if (eventType.getEventTypes() != null
								&& !eventType.getEventTypes().startsWith("processInstanceCompleted:")
								&& !eventType.getEventTypes().startsWith("payment")
								&& !eventType.getEventTypes().endsWith("Retry")
								) {
							processInstanceId = String.valueOf(eventType.getInstanceId());
							genericDTO.getStrAttributes().put("processInstanceIdToStart", processInstanceId);
							genericDTO.getStrAttributes().put("signalRef", String.valueOf(eventType.getEventTypes()));
							_logger.info("processInstanceIdToStart :"+ genericDTO.getStrAttributes().get("processInstanceIdToStart") 
									+ " : signalRef :"+ genericDTO.getStrAttributes().get("signalRef") 
									+ "sub_order_ID" + genericDTO.getStrAttributes().get("sub_order_id"));
							break;
						}

					}
				}
			}

		} catch (Exception e) {
			_logger.error(genericDTO.getTransactionId() + " | Exception occured in getCurrentProcessInstaceId " + e, e);
		} finally {
			HibernateUtils.closeSession(session);
			session = null;
			hql = null;
			query = null;
			criteria = null;
			eventTypes = null;
		}
		if (processInstanceId != null) {
			isFOWaiting = true;
		}
		_logger.info("FO is waiting for sub_order_id :" + genericDTO.getStrAttributes().get("sub_order_id")
				+ " : action :" + genericDTO.getStrAttributes().get("sub_order_id") + " : isFOWaiting :" + isFOWaiting);
		return isFOWaiting;
	}

}
