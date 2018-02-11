'use strict';

angular.module('warehouseApp')
    .controller('StatusDetailController', function ($scope, $rootScope, $stateParams, entity, Status) {
        $scope.status = entity;
        $scope.load = function (id) {
            Status.get({id: id}, function(result) {
                $scope.status = result;
            });
        };
        var unsubscribe = $rootScope.$on('warehouseApp:statusUpdate', function(event, result) {
            $scope.status = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
