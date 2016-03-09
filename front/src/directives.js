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
                        //$rootScope.range = null;
                        $rootScope.range = initRange(rangy.getSelection());
                    });
                })
            }
    }
});

//图片上传，此directive不会创建子的scope，会使用父scope
svenModule.directive('uploadFile', function ($upload, project) {
    return{
        restrict: 'EA',
        replace: false,
        templateUrl: 'src/templates/partial/upload-file.html',
        scope:false,
        link: function (scope) {
            scope.onImgSelect = function (type, $files) {
                var file = $files[0];
                $upload.upload({
                    url: project.uri + '/admin/picture/uploadImage',
                    method: 'POST',
                    file: file
                }).then(function (res) {
                    console.log(res.entity);
                    scope.entity.imgUrl = res.entity;
                }, function (rej) {
                    console.log('error');
                }, function (e) {
                    console.log('正在上传：' + parseInt(100 * e.loaded / e.total, 10));
                });
            }
        }
    }
})