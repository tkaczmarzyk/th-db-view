th-db-view
==========

Demo of loading view fragments from db with Thymeleaf and Spring MVC. You can find more details in the [article on my blog](http://blog.kaczmarzyk.net/2015/01/04/loading-view-templates-from-database-with-thymeleaf/).

Building and running
--------------------

It's a Maven project. After building it, you'll find an executable jar in the `target` directory. Just run it with `java -jar th-db-view.jar`. It will start an embedded server with an in-memory database.

Then enter this URL in your browser:

    http://localhost:8080/?name=YOUR_NAME

and you will see a boring greetings page. The interesting stuff is that you can add a new template to change the view. In order to do that, POST the new template content:

    curl http://localhost:8080/templates -d '<strong th:inline="text">something new, [[${name}]]!</strong>'

and check the hello page again. You should see the updated view. It's a very simple example, but the bottom line is that the template can use all the features of Thymeleaf.
