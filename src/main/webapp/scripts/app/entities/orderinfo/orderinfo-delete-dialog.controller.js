'use strict';

angular.module('warehouseApp')
	.controller('OrderinfoDeleteController', function($scope, $uibModalInstance, entity, Orderinfo) {

        $scope.orderinfo = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Orderinfo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
