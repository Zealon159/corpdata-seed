
数据源的切换方法：
	方式1，在service层方法上增加注解:
		@DynamicSwitchDataSource(dataSource = DataSourceEnum.ACTIVITI)
		public String functionName(){//do something}
	方式2，在service层类上增加注解:
		@DynamicSwitchDataSource(dataSource = DataSourceEnum.ACTIVITI)
		public class myService{}
	方式3，在service调用dao层前，进行编写数据源切换代码
		public String functionName(){
			DataSourceContextHolder.setTargetDataSource(DataSourceEnum.ACTIVITI);
			mapper.selectAll();
		}
	
