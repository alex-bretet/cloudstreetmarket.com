<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="false" %>

<!DOCTYPE html>
<html lang="en" ng-app="cloudStreetMarketApp">
<head>

	<!-- start: Meta -->
	<meta charset="utf-8">
	<title translate="screen.home.title">Cloudstreet Market! The financial and community portal [v8.0.0]</title> 
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
   	<link href="css/font-awesome.min.css" rel="stylesheet">
   	<link href="css/language/language.min.css" rel="stylesheet">
   	
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
													<a href="http://twitter.com/SpringMVCCookB"></a>
												</div>
												<div class="social-small-info-back social-small-twitter-hover">
													<a href="http://twitter.com/SpringMVCCookB"></a>
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
													<a href="https://www.facebook.com/springmvccookbook"></a>
												</div>
												<div class="social-small-info-back social-small-facebook-hover">
													<a href="https://www.facebook.com/springmvccookbook"></a>
												</div>	
											</div>
										</div>
									</div>
								</li>
								<li>
									<div class="social-small-item">				
										<div class="social-small-info-wrap">
											<div class="social-small-info">
												<div class="social-small-info-front social-small-amazon">
													<a href="http://www.amazon.co.uk/Spring-MVC-Cookbook-Alex-Bretet/dp/1784396419"></a>
												</div>
												<div class="social-small-info-back social-small-amazon-hover">
													<a href="http://www.amazon.co.uk/Spring-MVC-Cookbook-Alex-Bretet/dp/1784396419"></a>
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
								<li class="active"><a href="/portal/index" translate="webapp.main.menu.home">Home</a></li>
              					<li class="dropdown">
                					<a href="markets" class="dropdown-toggle"><span translate="webapp.main.menu.price.and.market">Prices and markets</span> <b class="caret"></b></a>
                					<ul class="dropdown-menu">
                						<li class="nav-header" translate="webapp.main.menu.indices">Indices</li>
                						<li><a href="/portal/indices-EUROPE"><i class="fa fa-table fa-fw" style="margin-right:5px;"></i> <span translate="webapp.main.menu.indices.by.market">Indices by markets</span></a></li>
                  						<li class="divider"></li>
                  						<li class="nav-header" translate="webapp.main.menu.stocks">Stocks</li>
                  						<li><a href="/portal/stock-search"><i class="fa fa-search fa-fw" style="margin-right:5px;"></i><span translate="webapp.main.menu.all.prices.search">All prices search</span></a></li>
                  						<li><a href="/portal/stock-search-by-market"><i class="fa fa-search fa-fw" style="margin-right:5px;"></i><span translate="webapp.main.menu.prices.search.by.market">Search by markets</span></a></li>
                  						<li><a href="/portal/stocks-risers-fallers"><i class="fa fa-line-chart fa-fw" style="margin-right:5px;"></i> <span translate="webapp.main.menu.prices.risers.and.fallers">Risers and fallers</span></a></li>
                					</ul>
              					</li>
								<li class="dropdown">
									<a href="community" class="dropdown-toggle"><span translate="webapp.main.menu.community">Community</span> <b class="caret"></b></a>
                					<ul class="dropdown-menu">
                  						<li><a href="/portal/all-users"><i class="fa fa-users fa-fw" style="margin-right:5px;"></i><span translate="webapp.main.menu.community.all.users">All users</span></a></li>
                  						<!-- <li><a href="/portal/leaderboard"><i class="fa fa-line-chart fa-fw" style="margin-right:5px;"></i><span translate="webapp.main.menu.community.leaderboard">Leaderboard</span></a></li> -->
                					</ul>
								</li>
								<li><a href="sources" translate="webapp.main.menu.sources">Sources</a></li>
								<li><a href="about" translate="webapp.main.menu.about">About</a></li>
								<li><a href="contact" translate="webapp.main.menu.contact">Contact</a></li>

							</ul>
								
				            <ul class="nav" style="float: right">
								<li ng-if="!userAuthenticated()"><a data-toggle="modal" href="" ng-click="menuModal('auth')">
										<i class="fa fa-lock" style="margin-right:5px;"></i>
										<span translate="webapp.main.menu.login">Login</span>
									</a>
								</li>
					            <li class="dropdown" ng-if="userAuthenticated()">
					            	<div id="authenticatedDiv" class="hide"></div>
                					<a href="markets" class="dropdown-toggle"><i class="fa fa-user fa-fw" style="margin-right:5px;"></i> {{loggedInUser}} <b class="caret"></b></a>
                					<ul class="dropdown-menu">
                						<li><a href="/portal/preferences"><i class="fa fa fa-pencil fa-fw" style="margin-right:5px;"></i> <span translate="webapp.main.menu.preferences">Preferences</span> </a></li>					
                						<li><a href="/portal/wallet"><i class="fa fa-book fa-fw" style="margin-right:5px;"></i> <span translate="webapp.main.menu.wallet">Wallet</span> </a></li>
                						<li class="divider"></li>
                						<li><a href="" ng-click="logout()"><i class="fa fa-sign-out fa-fw" style="margin-right:5px;"></i><span translate="webapp.main.menu.logout">Logout</span></a></li>
                					</ul>
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
										<li><a href="index" translate="webapp.main.menu.home">Home</a></li>
										<li><a href="markets" translate="webapp.main.menu.price.and.market">Prices and markets</a></li>
										<li><a href="community" translate="webapp.main.menu.community">Community</a></li>
										<li><a href="sources" translate="webapp.main.menu.sources">Sources</a></li>
										<li><a href="about" translate="webapp.main.menu.about">About</a></li>
										<li><a href="contact" translate="webapp.main.menu.contact">Contact</a></li>
										<li><a data-toggle="modal" href="#authModal" translate="webapp.main.menu.login">Login</a></li>
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
							<h3 translate="webapp.footer.about.us">About Us</h3>
							<p translate="webapp.footer.about.us.blah.blah">Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat.</p>
						</div>
						<!-- end: About -->

						<!-- start: Leaderboard -->
						<div class="span3" ng-controller="leaderBoardFooterController">
							<h3 translate="webapp.footer.leaderboard">Leaderboard</h3>
							<div class="flickr-widget">
								<div class="flickr_badge_image" id="flickr_badge_image{{$index}}" data-ng-repeat="value in leaders">
									<a href="http://cloudstreetmarket.com/portal/user-{{value.id}}"><img src="{{value.profileImg}}" alt="{{value.id}}" title="{{value.id}}" height="75" width="75"></a>
								</div>
								<div class="clear"></div>
							</div>
						</div>
						<!-- end: Leaderboard -->
				
						<div class="span6">
					
							<!-- start: Follow Us -->
							<h3 translate="webapp.footer.follow.us">Follow Us!</h3>
							<ul class="social-grid">
								<li>
									<div class="social-item">				
										<div class="social-info-wrap">
											<div class="social-info">
												<div class="social-info-front social-twitter">
													<a href="http://twitter.com/SpringMVCCookB"></a>
												</div>
												<div class="social-info-back social-twitter-hover">
													<a href="http://twitter.com/SpringMVCCookB"></a>
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
													<a href="https://www.facebook.com/springmvccookbook"></a>
												</div>
												<div class="social-info-back social-facebook-hover">
													<a href="https://www.facebook.com/springmvccookbook"></a>
												</div>
											</div>
										</div>
									</div>
								</li>
								<li>
									<div class="social-item">				
										<div class="social-info-wrap">
											<div class="social-info">
												<div class="social-info-front social-amazon">
													<a href="http://www.amazon.co.uk/Spring-MVC-Cookbook-Alex-Bretet/dp/1784396419"></a>
												</div>
												<div class="social-info-back social-amazon-hover">
													<a href="http://www.amazon.co.uk/Spring-MVC-Cookbook-Alex-Bretet/dp/1784396419"></a>
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
								<h3 translate="webapp.footer.newsletter">Newsletter</h3>
								<p translate="webapp.footer.please.your.email">Please leave us your email</p>
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
			<p><a href="http://bootstrapmaster.com">Bootstrap Themes</a> by BootstrapMaster, thanks</p>
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
	<script src="js/angular/angular-translate.min.js"></script>
	<script src="js/angular/angular-translate-loader-url.min.js"></script>
	<script src="js/util/ui-bootstrap-tpls-0.11.0.min.js"></script>
	<script src="js/util/sockjs-1.0.3.js"></script>
	<script src="js/util/stomp-2.3.3.js"></script>
	
	<script>
		var cloudStreetMarketApp = angular.module('cloudStreetMarketApp', ['ngRoute','ui.bootstrap', 'pascalprecht.translate', 'ngCookies']);
		
		cloudStreetMarketApp.config(function ($translateProvider) {
			    $translateProvider.useUrlLoader('/api/properties.json');
			    $translateProvider.useStorage('UrlLanguageStorage');
			    $translateProvider.preferredLanguage('en');
			    $translateProvider.useSanitizeValueStrategy(null);
			    $translateProvider.fallbackLanguage('en');
			});

	</script>
	
	<script src="js/global_routes.js"></script>
	
	<script src="js/auth/http_authorized.js"></script>
	<script src="js/auth/account_management.js"></script>
	<script src="js/modals.js"></script>
	<script src="js/main_menu.js"></script>
	<script src="js/leaderboard_footer.js"></script>
	<script src="js/home/home_financial_graph.js"></script>
	<script src="js/home/home_financial_table.js"></script>
	<script src="js/home/home_community_activity.js"></script>
	<script src="js/indices/indices_by_markets.js"></script>
	<script src="js/indices/index_detail.js"></script>
	<script src="js/stocks/stock_detail.js"></script>
	<script src="js/stocks/stock_search.js"></script>
	<script src="js/stocks/stock_search_by_market.js"></script>
	<script src="js/stocks/stocks_risers_fallers.js"></script>
	<script src="js/community/all_users.js"></script>
	<script src="js/community/user_profile.js"></script>
	<script src="js/wallet/wallet.js"></script>
	
	<!-- end: Javascript -->

	<!-- Google analytics -->
		<script>
		  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
		  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
		  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
		  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
		
		  ga('create', 'UA-7628104-14', 'auto');
		  ga('require', 'linkid', 'linkid.js');
		  ga('send', 'pageview');
		</script>
	<!-- Google analytics -->
	
</body>
</html>
