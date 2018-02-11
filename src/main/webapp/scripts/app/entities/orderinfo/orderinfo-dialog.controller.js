'use strict';

angular.module('warehouseApp').controller('OrderinfoDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Orderinfo', 'Status', 'Customer',
        function($scope, $stateParams, $uibModalInstance, entity, Orderinfo, Status, Customer) {

        $scope.orderinfo = entity;
        $scope.statuss = Status.query();
        $scope.customers = Customer.query();
        $scope.load = function(id) {
            Orderinfo.get({id : id}, function(result) {
                $scope.orderinfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('warehouseApp:orderinfoUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.orderinfo.id != null) {
                Orderinfo.update($scope.orderinfo, onSaveSuccess, onSaveError);
            } else {
                Orderinfo.save($scope.orderinfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForDatecreate = {};

        $scope.datePickerForDatecreate.status = {
            opened: false
        };

        $scope.datePickerForDatecreateOpen = function($event) {
            $scope.datePickerForDatecreate.status.opened = true;
        };
}]);
