<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="fragments/header.jsp"/>
</head>

<body>
    <div id="wrap">

        <%= "Hello World!" %>

        <div class="container">
            <div class="row">
                <div class="col-md-2">
                  <div class="well sidebar-nav">
                    
                  </div>
                </div>

                <div class="col-md-10">
                    <div class="jumbotron">
                     
                    </div>

                    <div class="row">
                      <div class="col-md-12">
                          <div class="container">
                            <iframe class="github-btn" src="http://ghbtns.com/github-btn.html?user=priyatam&repo=springmvc-twitterbootstrap-showcase&type=watch&count=true" allowtransparency="true" frameborder="0" scrolling="0" width="100px" height="20px"></iframe>

                            <a href="https://twitter.com/share" class="twitter-share-button" data-url="http://springmvc-twitterbootstrap-showcase.cloudfoundry.com" data-text="Spring MVC Twitter Bootstrap Showcase!">Tweet</a>
                            <script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0];if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src="//platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>
                          </div>

                        <hr class="soften">

                       <!-- View index.vm is inserted here -->
						

                      </div>
                    </div><!--/col-->
                </div><!--/row-->
            </div><!--/col-->
          </div><!--/row-->

          <hr class="soften">
    </div>

   

	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/demo.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/json2.js" />"></script>
  	<script type="text/javascript" src="<c:url value="/resources/js/date.format.js" />"></script>
</body>
</html>
