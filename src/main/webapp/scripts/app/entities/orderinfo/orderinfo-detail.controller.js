'use strict';

angular.module('warehouseApp')
    .controller('OrderinfoDetailController', function ($scope, $rootScope, $stateParams, entity, Orderinfo, Status, Customer) {
        $scope.orderinfo = entity;
        $scope.load = function (id) {
            Orderinfo.get({id: id}, function(result) {
                $scope.orderinfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('warehouseApp:orderinfoUpdate', function(event, result) {
            $scope.orderinfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
