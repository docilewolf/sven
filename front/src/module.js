/*global Modules:true*/

/*var commonResolve = {
  delay: function ($q) {
    var delay = $q.defer(),
    load = function () {
      $.getScript('public/timeline/js/main.js', function () {
        delay.resolve();
      });
    };
    load();
    return delay.promise;
  }
}*/

// config routes module
svenModule.config(function($stateProvider, $urlRouterProvider) {

  $urlRouterProvider.otherwise("/welcome");

  $stateProvider
    .state('welcome', {
        url: '/welcome',
        templateUrl: 'src/templates/welcome.html',
        controller: 'indexController'
    })
    .state('account', {
      url: '/account',
      templateUrl: 'src/templates/account.html',
      controller: 'accountController'
    })
    .state('category', {
      url: '/category/{type:[0-9]+}/{id:[0-9a-zA-Z]+}',
      templateUrl: "src/templates/category.html",
      controller: 'categoryController'
    })
    .state('comment', {
      url: '/comment',
      templateUrl: "modules/sven/templates/comment.html",
      controller: 'commentController'
    })
    .state('essay', {
      url: '/essay/{id:[0-9a-zA-Z]+}',
      templateUrl: "src/templates/essay.html",
      controller: 'essayController'
    })
    .state('newEssay', {
      url: '/newEssay',
      templateUrl: "src/templates/newEssay.html",
      controller: 'newEssayController'
    })
    .state('editEssay', {
      url: '/editEssay/{id:[0-9]+}',
      templateUrl: "src/templates/newEssay.html",
      controller: 'newEssayController'
    })
    .state('member', {
      url: '/member',
      templateUrl: "modules/sven/templates/member.html",
      controller: 'memberController'
    })
    .state('picture', {
      url: '/picture',
      templateUrl: "src/templates/picture.html",
      controller: 'pictureController'
    })
    .state('newPicture', {
      url: '/newPicture',
      templateUrl: "src/templates/newPicture.html",
      controller: 'newPictureController'
    })
    .state('editPicture', {
      url: '/editPicture/{id:[0-9]+}',
      templateUrl: "src/templates/newPicture.html",
      controller: 'newPictureController'
    })
    .state('profile', {
      url: '/profile/{type:[0-9]+}',
      templateUrl: "src/templates/category.html",
      controller: 'profileController'
    });
});