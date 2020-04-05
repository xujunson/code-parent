1、（彩蛋番外篇五）关于 JPA，你知道多少
1.1、你需要知道 Spring Data Jpa 的概念和思想
1)、什么是JPA？
即Java Persistence API，用于对象持久化的API，它是ORM规范，使得应用程序以统一的方式访问持久层。
![binaryTree](../img/什么是JPA.png "binaryTree")

2)、JPA的优势
a、标准化：提供了相同的API，保证了基于JPA开发的企业应用能够经过少量的修改就能够在不同的框架之下去运行。
b、简单易用：JPA的主要目标之一就是提供更加简单的编程模型，在JPA框架下去创建实体和Java类是一样简单的，只需要使用"@Entity"注解；
JPA定义了独特的JPQL，而且能够支持批量更新和修改，这种group by等等通常只有SQL才能够提供的高级查询特性，
在JPA里面也能够实现；甚至它还能够支持子查询。但是一般不去利用JPA做复杂查询。
c、面向对象：JPA支持面向对象的高级特性，比方说类之间的继承、多态和类之间的复杂关系。它最大限度的使用面向对象的模型；

3)、JPA包含的技术
a、ORM映射元数据：JPA支持xml和jdk5.0里面的注解两种元数据定义的形式。元数据就是描述对象和表之间的映射关系；
b、提供了查询API：用来操作实体对象来执行CRUD操作，框架在后台完成所有的事情，开发者能够从繁琐的JDBC和SQL代码中解脱出来；
c、查询语言(JPQL)：通过面向对象而不是面向数据的查询语言去查询数据，它能够避免应用程序和具体的SQL代码紧密的耦合在一起；

4)、Spring Data项目
![binaryTree](../img/SpringData项目.png "binaryTree")
![binaryTree](../img/涉及的内容.png "binaryTree")

5)、数据库连接池
![binaryTree](../img/数据库连接池.png "binaryTree")
![binaryTree](../img/数据库连接池的优势.png "binaryTree")
![binaryTree](../img/SpringBoot2默认的数据库连接池.png "binaryTree")
![binaryTree](../img/SpringBoot2 HikariCP的配置.png "binaryTree")