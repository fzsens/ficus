##Ficus
缘起是一个小型的项目，因为需要使用JdbcTemplate作为ORM技术，为了简化开发，利用一点业余时间完成的一个基于Spring JdbcTemplate的基础上完成的，ORM小工具。  
底层基于Spring JdbcTemplate实现对数据库层的操作，利用Reflection技术，完成类似Hibernate 的Criteria风格的操作以及关系对象映射。在此基础上，实现一个通用的Dao层，用于完成单表的CRUD操作，从而简化了简单的数据库操作；在复杂SQL的处理上，移植并删减了Mybatis的XML解析功能，通过`refId`实现从XML到数据库操作的映射；同时利用SQL解析提供了一些简单的读写分离策略的支持。  
工具整体结构十分简单，可以通过`test`工程查看如何集成和使用

###工具有下面几个主要的组成部分  
- ficus-common 提取了一些常用的工具类
- ficus-dal 主要实现ORM以及Criteria面向对象风格的支持
- ficus-dal-batis 实现了Mybaits风格的SQL编写支持
- ficus-dal-jdbc 基于JdbcTemplate实现了数据库操作接口抽象和读写分离支持
- ficus-sqlparser SQL解析和优化，这部分目前尚未完成