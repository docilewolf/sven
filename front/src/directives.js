//div内容改变可以通知到angular完成数据绑定
svenModule.directive('contenteditable', function() {
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function(scope, element, attrs, ngModel) {
            // view -> model
            element.bind('blur keyup change', function() {
                scope.$apply(function() {
                    ngModel.$setViewValue(element.html());
                });
            });

            // model -> view
            ngModel.$render = function() {
                element.html(ngModel.$viewValue);
            };
        }
    };
});

//修改字体样式 B, i, strike, h1, h2, h3, h4
svenModule.directive('font', function() {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            element.bind('click', function(e) {
                var selection = rangy.getSelection();
                if (attrs.fontStyle) {
                    document.execCommand(attrs.fontStyle);
                }
                if (attrs.formatBlock) {
                    var changeNode = isFirstParentChange(selection.anchorNode, attrs.formatBlock);
                    if(changeNode){
                        var newNode = document.createElement('p');
                        newNode.innerHTML = changeNode[0].innerHTML;
                        $(changeNode[0]).replaceWith('<p>' + $(changeNode[0]).html() + '</p>');
                    }else{
                        document.execCommand("formatBlock", false, attrs.formatBlock);
                    }
                }
                if(attrs.fontHr){
                    var node = getOutsideNode(selection.anchorNode);
                    if($(node).next()[0] && $(node).next()[0].tagName.toLowerCase() == 'hr'){
                        $(node).next().remove();
                    }else {
                        $(node).after('<hr/>');
                    }
                }
                e.stopPropagation();
            });

            function isFirstParentChange(node, tag){
                while(!node.id && node.id !== 'content-area'){
                    if(node.tagName && node.tagName.toLowerCase() == tag.toLowerCase()){
                        return [node, node.parentNode];
                    }
                    node = node.parentNode;
                }

                return false;
            }

            function getOutsideNode(node){
                while(!node.parentNode.id && node.parentNode.id !== 'content-area'){
                    node = node.parentNode;
                }
                return node;
            }
        }
    }
});

//插入内容 a, img
svenModule.directive('link', function ($rootScope) {
    return {
        restrict: 'A',
        scope:false,
        link: function (scope, element) {
                element.bind('click', function (event) {
                    scope.$apply(function () {
                        $rootScope.range = initRange(rangy.getSelection());
                    });
                })
            }
    }
});

//图片上传，此directive不会创建子的scope，会使用父scope
svenModule.directive('uploadFile', function () {
    return{
        restrict: 'EA',
        replace: false,
        templateUrl: 'src/templates/partial/upload-file.html',
        scope:false,
        link: function (scope) {
            scope.onImgSelect = function (type, $files) {
                scope.entity.imgUrl = "http://devfront.qdingnet.com/public/wallpaper/5.jpg";
                /**
                 upload: function ($files) {
      var deferred = $q.defer();
      var file = $files[0];
      $injector.get('$upload').upload({
        url: '/sysConfig/admin/upload/publicImageUpload',
        method: 'POST',
        file: file
      }).then(function (res) {
        deferred.resolve(res.imageUrl);
        growl.addSuccessMessage('上传成功');
      }, function (rej) {
        growl.addErrorMessage(rej.message || '上传失败');
        deferred.reject(rej);
      }, function (e) {
        growl.addInfoMessage('正在上传：' + parseInt(100 * e.loaded / e.total, 10));
      });
      return deferred.promise;
    },
                 *
                 */
                console.log($files[0]);
            }
        }
    }
})