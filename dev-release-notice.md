## 开发部署注意事项

##### 开发打包时注意
- 请将*res*目录下的内容加入包内*WEB-INF/classes*目录下
- 使用IDEA连接数据库自动映射POJO后，请确保hibernate.cfg.xml中的数据库连接配置正确

##### 部署时注意
- 请确保区块链节点已经启动
- 首次部署手动调用general.controller.*EnterpriseController*#*createTokenAccount*(...)方法为每一个企业初始化Token钱包