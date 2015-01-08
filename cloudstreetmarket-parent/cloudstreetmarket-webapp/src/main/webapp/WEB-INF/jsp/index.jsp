<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en" ng-app="cloudStreetMarketApp">
<head>

	<!-- start: Meta -->
	<meta charset="utf-8">
	<title>FreeME:BOOTSTRAP THEME by BootstrapMaster</title> 
	<meta name="description" content="FreeME:Bootstrap Theme"/>
	<meta name="keywords" content="Template, Theme, web, html5, css3" />
	<meta name="author" content="Ãï¿½ukasz Holeczek from creativeLabs"/>
	<!-- end: Meta -->
	
	<!-- start: Mobile Specific -->
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<!-- end: Mobile Specific -->
	
	<!-- start: Facebook Open Graph -->
	<meta property="og:title" content=""/>
	<meta property="og:description" content=""/>
	<meta property="og:type" content=""/>
	<meta property="og:url" content=""/>
	<meta property="og:image" content=""/>
	<!-- end: Facebook Open Graph -->

    <!-- start: CSS -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/bootstrap-responsive.css" rel="stylesheet">
	<link href="css/style.css" rel="stylesheet">
	<link href="css/FeedEk.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Droid+Sans:400,700">
	<link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Droid+Serif">
	<link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Boogaloo">
	<link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Economica:700,400italic">
	<!-- end: CSS -->

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

</head>
<body>
	
	<!--start: Wrapper -->
	<div id="wrapper">
		
		<!--start: Container -->
		<div class="container">

			<!--start: Header -->
			<header>
			
				<!--start: Row -->
				<div class="row">
					
					<!--start: Logo -->
					<div class="logo span4">
							CLOUD<span class="sub">ST</span><span>Market</span>
					</div>
					<!--end: Logo -->

					<!--start: Social Links -->
					<div class="span8">
						<div id="social-links">
							<ul class="social-small-grid">
								<li>
									<div class="social-small-item">				
										<div class="social-small-info-wrap">
											<div class="social-small-info">
												<div class="social-small-info-front social-small-twitter">
													<a href="http://twitter.com"></a>
												</div>
												<div class="social-small-info-back social-small-twitter-hover">
													<a href="http://twitter.com"></a>
												</div>	
											</div>
										</div>
									</div>
								</li>
								<li>
									<div class="social-small-item">				
										<div class="social-small-info-wrap">
											<div class="social-small-info">
												<div class="social-small-info-front social-small-facebook">
													<a href="http://facebook.com"></a>
												</div>
												<div class="social-small-info-back social-small-facebook-hover">
													<a href="http://facebook.com"></a>
												</div>	
											</div>
										</div>
									</div>
								</li>
								<li>
									<div class="social-small-item">				
										<div class="social-small-info-wrap">
											<div class="social-small-info">
												<div class="social-small-info-front social-small-dribbble">
													<a href="http://dribbble.com"></a>
												</div>
												<div class="social-small-info-back social-small-dribbble-hover">
													<a href="http://dribbble.com"></a>
												</div>	
											</div>
										</div>
									</div>
								</li>
								<li>
									<div class="social-small-item">				
										<div class="social-small-info-wrap">
											<div class="social-small-info">
												<div class="social-small-info-front social-small-flickr">
													<a href="http://flickr.com"></a>
												</div>
												<div class="social-small-info-back social-small-flickr-hover">
													<a href="http://flickr.com"></a>
												</div>	
											</div>
										</div>
									</div>
								</li>
							</ul>
						</div>
					</div>
					<!--end: Social Links -->
					
				</div>
				<!--end: Row -->
						
			</header>
			<!--end: Header-->
			
			<!--start: Navigation-->	
			<div class="navbar navbar-inverse">
    			<div class="navbar-inner">
        			<div class="container">
          				<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            				<span class="icon-bar"></span>
            				<span class="icon-bar"></span>
            				<span class="icon-bar"></span>
          				</a>
          				<div class="nav-collapse collapse">
							<ul class="nav">
								<li class="active"><a href="index">Home</a></li>
								<li><a href="markets">Prices and markets</a></li>
								<li><a href="community">Community</a></li>
								<li><a href="sources">Sources</a></li>
								<li><a href="about">About</a></li>
								<li><a href="contact">Contact</a></li>
							</ul>
          				</div>
        			</div>
      			</div>
    		</div>
			<!--end: Navigation-->
			
		</div>
		<!--end: Container-->
				
		<!--start: Container -->
    	<div class="container">

			<!-- start: Row -->
			<div class="row">
				<div class="span12">
					<div class="hero-unit hidden-phone">
						<p>Welcome to CloudStreet Market, the educational platform.</p>
					</div>
				</div>
				<div class="span5">
			
			
					<div id="landingGraphContainerAndTools">
							<div id='landingGraphContainer' ng-controller="homeFinancialGraphController">
								<select class="form-control centeredElementBox">
									<option value="${dailyMarketActivity.marketId}"> ${dailyMarketActivity.marketShortName}</option>
								</select> 
							</div>
	
							<div id='tableMarketPrices'>		
									<script>
										var dailyMarketsActivity = [];
										var market;
									</script>
							
									<c:forEach var="market" items="${dailyMarketsActivity}">
										<script>
											market = {};
											market.marketShortName = '${market.marketShortName}';
											market.latestValue = (${market.latestValue}).toFixed(2);
											market.latestChange = (${market.latestChange}*100).toFixed(2);
											dailyMarketsActivity.push(market);
										</script>
								 	</c:forEach>
								 
									<div>
										<table class="table table-hover table-condensed table-bordered table-striped" data-ng-controller='homeFinancialTableController'>
											<thead>
												<tr>
													<th>Index</th>
													<th>Value</th>
													<th>Change</th>
												</tr>
											</thead>
											<tbody>
												<tr data-ng-repeat="value in financialMarkets">
													<td>{{value.marketShortName}}</td>
													<td style="text-align:right">{{value.latestValue}}</td>
													<td class='{{value.style}}' style="text-align:right"><strong>{{value.latestChange}}%</strong></td>
												</tr>
											</tbody>
										</table>
									</div>
							</div>	
	
					</div>
				</div>
				<div class="span7">

					<div id="divRss3">
						<ul class="feedEkList" data-ng-controller='homeCommunityActivityController'>
					
							  <script>
									var userActivities = [];
									var userActivity;
							  </script>
									
						      <c:forEach var="activity" items="${recentUserActivity}">
								  <script>
										userActivity = {};
										userActivity.userAction = '${activity.userAction}';
										userActivity.urlProfilePicture = '${activity.urlProfilePicture}';
										userActivity.userName = '${activity.userName}';
										userActivity.urlProfilePicture = '${activity.urlProfilePicture}';
										userActivity.date = '<fmt:formatDate value="${activity.date}" pattern="dd/MM/yyyy hh:mm aaa"/>';
										userActivity.userActionPresentTense = '${activity.userAction.presentTense}';
										userActivity.amount = ${activity.amount};
										userActivity.valueShortId = '${activity.valueShortId}';
										userActivity.price = (${activity.price}).toFixed(2);
										userActivities.push(userActivity);
								  </script>
						      </c:forEach>
							      
							  <li data-ng-repeat="value in communityActivities">
									<div class="itemTitle">
										<div class="listUserIco {{value.defaultProfileImage}}">
											<img ng-if="value.urlProfilePicture" src='{{value.urlProfilePicture}}'>
										</div>
										<span class="ico-white {{value.iconDirection}} listActionIco"></span>
						
										<a href="#">{{value.userName}}</a> 
										{{value.userActionPresentTense}} {{value.amount}} 
										<a href="#">{{value.valueShortId}}</a> at {{value.price}}
										<p class="itemDate">{{value.date}}</p>
									</div>
							  </li>
						</ul>
					</div>

				</div>

			</div>
			<!-- end: Row -->
      		
			<hr class="hidden-phone">
		
			<!-- start Clients List -->	
			<div class="clients-carousel hidden-phone">
		
				<ul class="slides clients">
					<li><img src="img/logos/1.png" alt=""/></li>
					<li><img src="img/logos/2.png" alt=""/></li>
					<li><img src="img/logos/3.png" alt=""/></li>	
					<li><img src="img/logos/4.png" alt=""/></li>
					<li><img src="img/logos/5.png" alt=""/></li>
					<li><img src="img/logos/6.png" alt=""/></li>
				</ul>
		
			</div>
			<!-- end Clients List -->
		
			<hr>
			
		</div>
		<!--end: Container-->
				
		<!--start: Container -->
    	<div class="container">		

      		<!-- start: Footer Menu -->
			<div id="footer-menu" class="hidden-tablet hidden-phone">

				<!-- start: Container -->
				<div class="container">
				
					<!-- start: Row -->
					<div class="row">

						<!-- start: Footer Menu Logo -->
						<div class="span1">
							<div class="logoSmall">
								CLOUD<span class="sub">ST</span><span>M!</span>
							</div>
						</div>

						<!-- end: Footer Menu Logo -->					
						<!-- start: Footer Menu Links-->
						<div class="span10">
								<div id="footer-menu-links">
									<ul id="footer-nav" style="margin-left:35pt;">
										<li><a href="index">Home</a></li>
										<li><a href="markets">Prices and markets</a></li>
										<li><a href="community">Community</a></li>
										<li><a href="sources">Sources</a></li>
										<li><a href="about">About</a></li>
										<li><a href="contact">Contact</a></li>
									</ul>
								</div>
						</div>
						<!-- end: Footer Menu Links-->
						<!-- start: Footer Menu Back To Top -->
						<div class="span1">
							<div id="footer-menu-back-to-top">
								<a href="#"></a>
							</div>
						</div>
						<!-- end: Footer Menu Back To Top -->

					</div>
					<!-- end: Row -->
				
				</div>
				<!-- end: Container  -->	

			</div>	
			<!-- end: Footer Menu -->

			<!-- start: Footer -->
			<div id="footer">
			
				<!-- start: Container -->
				<div class="container">
				
					<!-- start: Row -->
					<div class="row">

						<!-- start: About -->
						<div class="span3">
						
							<h3>About Us</h3>
							<p>
								Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat.
							</p>
							
						</div>
						<!-- end: About -->

						<!-- start: Leaderboard -->
						<div class="span3">
						<h3>Leaderboard</h3>
						<div class="flickr-widget">
						<script type="text/javascript" src=""></script>
						<div class="clear"></div>
						</div>
						</div>
						<!-- end: Leaderboard -->
				
						<div class="span6">
					
							<!-- start: Follow Us -->
							<h3>Follow Us!</h3>
							<ul class="social-grid">
								<li>
									<div class="social-item">				
										<div class="social-info-wrap">
											<div class="social-info">
												<div class="social-info-front social-twitter">
													<a href="http://twitter.com"></a>
												</div>
												<div class="social-info-back social-twitter-hover">
													<a href="http://twitter.com"></a>
												</div>	
											</div>
										</div>
									</div>
								</li>
								<li>
									<div class="social-item">				
										<div class="social-info-wrap">
											<div class="social-info">
												<div class="social-info-front social-facebook">
													<a href="http://facebook.com"></a>
												</div>
												<div class="social-info-back social-facebook-hover">
													<a href="http://facebook.com"></a>
												</div>
											</div>
										</div>
									</div>
								</li>
								<li>
									<div class="social-item">				
										<div class="social-info-wrap">
											<div class="social-info">
												<div class="social-info-front social-dribbble">
													<a href="http://dribbble.com"></a>
												</div>
												<div class="social-info-back social-dribbble-hover">
													<a href="http://dribbble.com"></a>
												</div>	
											</div>
										</div>
									</div>
								</li>
								<li>
									<div class="social-item">				
										<div class="social-info-wrap">
											<div class="social-info">
												<div class="social-info-front social-flickr">
													<a href="http://flickr.com"></a>
												</div>
												<div class="social-info-back social-flickr-hover">
													<a href="http://flickr.com"></a>
												</div>	
											</div>
										</div>
									</div>
								</li>
							</ul>
							<!-- end: Follow Us -->
					
							<!-- start: Newsletter -->
							<form id="newsletter">
								<h3>Newsletter</h3>
								<p>Please leave us your email</p>
								<label for="newsletter_input">@:</label>
								<input type="text" id="newsletter_input"/>
								<input type="submit" id="newsletter_submit" value="submit">
							</form>
							<!-- end: Newsletter -->
					
						</div>
					
					</div>
					<!-- end: Row -->	
				
				</div>
				<!-- end: Container  -->

			</div>
			<!-- end: Footer -->
	
		</div>
		<!-- end: Container  -->

	</div>
	<!-- end: Wrapper  -->

	<!-- start: Copyright -->
	<div id="copyright">
		<!-- start: Container -->
		<div class="container">
	<p><a href="http://bootstrapmaster.com" alt="Bootstrap Themes">Bootstrap Themes</a> by BootstrapMaster, thanks</p>
		</div>
		<!-- end: Container  -->
	</div>	
	<!-- end: Copyright -->
	
	<!-- start: Javascript -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="js/jquery-1.8.2.js"></script>
	<script src="js/bootstrap.js"></script>
	<script src="js/flexslider.js"></script>
	<script src="js/carousel.js"></script>
	<script def src="js/custom.js"></script>
	<script src="js/FeedEk.js"></script>
	<script src="js/raphael.js"></script>
	<script src="js/morris.min.js"></script>
	<script src="js/angular.min.js"></script>
	
	<script>
		var financial_data = [];
		<c:forEach var="dailySnapshot" items="${dailyMarketActivity.values}">
			financial_data.push({"period": '<c:out value="${dailySnapshot.key}"/>', "index": <c:out value='${dailySnapshot.value}'/>});
		</c:forEach>
	</script>

	<script>
		var cloudStreetMarketApp = angular.module('cloudStreetMarketApp', []);
		var tmpYmax = <c:out value="${dailyMarketActivity.maxValue}"/>;
		var tmpYmin = <c:out value="${dailyMarketActivity.minValue}"/>;
	</script>
	
	<script src="js/home_financial_graph.js"></script>
	<script src="js/home_financial_table.js"></script>
	<script src="js/home_community_activity.js"></script>
	<!-- end: Javascript -->

</body>
</html>
