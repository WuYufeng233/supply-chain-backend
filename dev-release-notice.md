## 打包时请注意

- 请将*res*目录下的内容加入包内*WEB-INF/classes*目录下，包括ca.crt node.crt node.key hibernate.cfg.xml 以及各个实体的orm配置文件
- 请将*src/main/webapp/WEB-INF*内的applicationContext.xml加入包内*WEB-INF/classes*目录下
- 使用IDEA连接数据库自动映射POJO后，请确保hibernate.cfg.xml中的数据库连接配置正确