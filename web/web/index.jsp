<%--
  Created by IntelliJ IDEA.
  User: jakob
  Date: 01.10.2016
  Time: 18:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>EJBTestWeb</title>
  </head>
  <body>
  Hello EJB Test
  <p>
    <a href="http://localhost:8080/EJBTestWeb/test.do">http://localhost:8080/EJBTestWeb/test.do</a>
  </p>

  <p>
    <a href="http://localhost:8080/EJBTestWeb/singleton.do">http://localhost:8080/EJBTestWeb/singleton.do</a> User = test is ok, User = test2 fails
  </p>

  <p>
    <a href="http://localhost:8080/EJBTestWeb/mdb.do?topic=hello topic">http://localhost:8080/EJBTestWeb/mdb.do topic</a>
  </p>
  <p>
    <a href="http://localhost:8080/EJBTestWeb/mdb.do?queue=hello queue">http://localhost:8080/EJBTestWeb/mdb.do queue</a>
  </p>
  <p>
    <a href="http://localhost:8080/EJBTestWeb/mdb.do?queue=error">http://localhost:8080/EJBTestWeb/mdb.do queue error</a>
  </p>

  <p>
    <a href="http://localhost:8080/EJBTestWeb/multi.do">http://localhost:8080/EJBTestWeb/multi.do</a>
  </p>

  <p>
    <a href="http://localhost:8080/EJBTestWeb/intercept.do">http://localhost:8080/EJBTestWeb/intercept.do</a>
  </p>

  <p>
    <a href="http://localhost:8080/EJBTestWeb/stateful.do">http://localhost:8080/EJBTestWeb/stateful.do</a>
  </p>

  </body>
</html>
