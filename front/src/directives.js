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
                if(!isInEditArea()) return;
                var selection = rangy.getSelection();
                if (attrs.fontStyle) {
                    document.execCommand(attrs.fontStyle);
                }
                if (attrs.formatBlock) {
                    var changeNode = isFirstParentChange(selection.anchorNode, attrs.formatBlock);
                    if(changeNode){
                        document.execCommand("formatBlock", false, 'p'); //此种写法可代替下边这种写法
                        //var newNode = document.createElement('p');
                        //newNode.innerHTML = changeNode[0].innerHTML;
                        //$(changeNode[0]).replaceWith('<p>' + $(changeNode[0]).html() + '</p>');
                    }else{
                        document.execCommand("formatBlock", false, attrs.formatBlock);
                        //document.execCommand("formatBlock", false, 'p');
                        //document.execCommand("insertHTML", false, "<p><br></p>")
                        //blockNode.innerHTML = changeNode[0].innerHTML;
                    }
                }
                if(attrs.fontHr){
                    var node = getOutsideNode(selection.anchorNode);
                    if($(node).next()[0] && $(node).next()[0].tagName.toLowerCase() == 'hr'){
                        $(node).next().remove(); //remove hr
                        $(node).next().remove(); //remove br
                    }else {
                        $(node).after('<hr/><br>');
                        //document.execCommand("insertHTML", false, "<p><br></p>")
                        //document.execCommand("formatBlock", false, 'p')
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
                while(!node &&!node.parentNode && !node.parentNode.id && node.parentNode.id !== 'content-area'){
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
        link: function (scope, element, attr) {
                element.bind('click', function (event) {
                    if(!isInEditArea()) return;
                    //$rootScope.range = null;
                    scope.$apply(function () {
                        $rootScope.range = rangy.getSelection().getRangeAt(0);
                        attr.linkState == 1?scope.newLink():scope.newPicture();
                    })
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
        link: function (scope, element, attr) {
            if(attr.multiple){
                scope.isMultiple = true;
                scope.list = [];
            }
            scope.onImgSelect = function (type, $files) {
                var param = {
                    url: project.uri + '/admin/picture/uploadImage',
                    method: 'POST',
                    fileFormDataName: 'files',
                    file: $files
                };
                $upload.upload(param).then(function (res) {
                    if(scope.isMultiple){
                        scope.list = scope.list.concat(res.list);
                    }else{
                        scope.entity.imgUrl = res.list[0];
                    }
                }, function (rej) {
                    console.log('error');
                }, function (e) {
                    console.log('正在上传：' + parseInt(100 * e.loaded / e.total, 10));
                });
            };
            
            scope.netWorkUrl = function () {
                var networkUrls = scope.entity.netWorkUrl.split(';');
                if(networkUrls.length <= 0) return;
                if(scope.isMultiple) {
                    var urls = [];
                    networkUrls.forEach(function (item) {
                        if (scope.list.indexOf(item) < 0 && urls.indexOf(item) < 0) urls.push(item)
                    });
                    scope.list = scope.list.concat(urls)
                }else{
                    scope.entity.imgUrl=networkUrls[0];
                }
            }
        }
    }
})