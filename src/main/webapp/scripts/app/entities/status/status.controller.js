'use strict';

angular.module('warehouseApp')
    .controller('StatusController', function ($scope, $state, Status) {

        $scope.statuss = [];
        $scope.loadAll = function() {
            Status.query(function(result) {
               $scope.statuss = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.status = {
                name: null,
                id: null
            };
        };
    });
