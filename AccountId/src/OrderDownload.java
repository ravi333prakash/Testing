
public class OrderDownload {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StringBuilder sql=new StringBuilder();
		String order_id="";
		String order_type=null;
		
		if(order_id!=null || order_type!=null) {
			sql.append("select xyz from OMC a, SOC b where a.orderId=b.orderId ");
		if(order_id!=null)
		{
			sql.append(" and ").append("a.orderId=").append("id");
		}
		if(order_type!=null)
		{
			sql.append(" and ").append("a.orderType=").append("type");
		}
		
		}
		else
		{
			sql.append("select xyz from OMC a, SOC b where a.orderId=b.orderId ");
		}
		String RAVEN_COUNTRY_CODE="636";
		int msisdnLength=3;
		String service_id="6366";
		if(service_id.startsWith(RAVEN_COUNTRY_CODE) && service_id.trim().length()>msisdnLength)
		{
			
			System.out.println("msisdn with CC , so after trimming "+service_id.substring(RAVEN_COUNTRY_CODE.length()));
		}
		String field="";
		if (field != null  &&  !field.trim().isEmpty()) {
			System.out.println("true");
		}

	//	System.out.println(sql);

	}

}
