<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en" ng-app="cloudStreetMarketApp">
<head>

	<!-- start: Meta -->
	<meta charset="utf-8">
	<title>Cloudstreet Market! The financial and community portal</title> 
	<meta name="description" content="Developed with Spring MVC Cookbook [PACKT Publishing] 2015"/>
	<meta name="keywords" content="Cloudstreet market, Spring, Spring MVC, educational, tutorial, Spring MVC Cookbook, PACKT Publishing" />
	<meta name="author" content="Alex Bretet"/>
	<!-- end: Meta -->

	<!-- start: Mobile Specific -->
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<!-- end: Mobile Specific -->

    <!-- start: CSS -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/bootstrap-responsive.css" rel="stylesheet">
    <link href="css/bootstrap-2.3.2.patch.css" rel="stylesheet">
   
	<link href="css/style.css" rel="stylesheet">
	<link href="css/FeedEk.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Droid+Sans:400,700">
	<link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Droid+Serif">
	<link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Boogaloo">
	<link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Economica:700,400italic">
	<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
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
        			<div class="container" ng-controller="menuController">
          				<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            				<span class="icon-bar"></span>
            				<span class="icon-bar"></span>
            				<span class="icon-bar"></span>
          				</a>
          				<div class="nav-collapse collapse">
							<ul class="nav">
								<li class="active"><a href="/portal/index">Home</a></li>
              					<li class="dropdown">
                					<a href="markets" class="dropdown-toggle"  data-toggle="dropdown">Prices and markets <b class="caret"></b></a>
                					<ul class="dropdown-menu">
                						<li class="nav-header">Indices</li>
                						<li><a href="/portal/indices-EUROPE">Indices by markets</a></li>
                  						<li class="divider"></li>
                  						<li class="nav-header">Stocks</li>
                  						<li><a href="/portal/stock-search">All prices search</a></li>
                  						<li><a href="/portal/stock-search-by-market">Search by markets</a></li>
                  						<li><a href="/portal/stocks-risers-fallers">Risers and fallers</a></li>
                					</ul>
              					</li>
              					<li class="dropdown">
                					<a href="markets" class="dropdown-toggle"  data-toggle="dropdown">My account <b class="caret"></b></a>
                					<ul class="dropdown-menu">
                						<li><a href="/portal/wallet">My wallet</a></li>
                						<li><a href="/portal/transactions">My transactions</a></li>
                					</ul>
              					
              					</li>
								<li><a href="community">Community</a></li>
								<li><a href="sources">Sources</a></li>
								<li><a href="about">About</a></li>
								<li><a href="contact">Contact</a></li>
								<li ng-if="!userAuthenticated()"><a data-toggle="modal" href="" ng-click="menuModal('auth')">
										<i class="fa fa-lock" style="margin-right:5px;"></i>
										Login
									</a>
								</li>
								<li ng-if="userAuthenticated()"><a href="" ng-click="logout()">
										Logout
									</a>
								</li>
							</ul>
          				</div>
          				<div id="spi" class="hide">${spi}</div>
        			</div>
      			</div>
    		</div>
			<!--end: Navigation-->
			
		</div>
		<!--end: Container-->
				
		<!--start: Container -->
    	<div class="container" id="mainContainer" ng-view>

		</div>
		<!--end: Container-->

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
										<li><a data-toggle="modal" href="#authModal"><i class="fa fa-lock" style="margin-right:5px;"></i>Login</a></li>
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
	<script src="js/util/jquery-1.8.2.js"></script>
	<script src="js/util/date.js"></script>
	<script src="js/util/bootstrap.js"></script>
	<script src="js/util/flexslider.js"></script>
	<script src="js/util/carousel.js"></script>
	<script def src="js/custom.js"></script>
	<script src="js/util/jquery.slimscroll.min.js"></script>

	<script src="js/angular/angular.min.js"></script>
	<script src="js/angular/angular-route.min.js"></script>
	<script src="js/angular/angular-cookies.min.js"></script>
	<script src="js/util/ui-bootstrap-tpls-0.11.0.min.js"></script>

	<script>
		var cloudStreetMarketApp = angular.module('cloudStreetMarketApp', ['ngRoute','ui.bootstrap']);
	</script>
	
	<script src="js/global_routes.js"></script>
	
	<script src="js/auth/http_authorized.js"></script>
	<script src="js/auth/account_management.js"></script>
	<script src="js/modals.js"></script>
	<script src="js/main_menu.js"></script>
	<script src="js/home/home_financial_graph.js"></script>
	<script src="js/home/home_financial_table.js"></script>
	<script src="js/home/home_community_activity.js"></script>
	<script src="js/indices/indices_by_markets.js"></script>
	<script src="js/indices/index_detail.js"></script>
	<script src="js/stocks/stock_detail.js"></script>
	<script src="js/stocks/stock_search.js"></script>
	<script src="js/stocks/stock_search_by_market.js"></script>
	<script src="js/stocks/stocks_risers_fallers.js"></script>
	<script src="js/my-account/wallet.js"></script>
	<script src="js/my-account/transactions.js"></script>
	<!-- end: Javascript -->

</body>
</html>
