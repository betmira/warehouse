'use strict';

describe('Controller Tests', function() {

    describe('Orderinfo Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockOrderinfo, MockStatus, MockCustomer;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockOrderinfo = jasmine.createSpy('MockOrderinfo');
            MockStatus = jasmine.createSpy('MockStatus');
            MockCustomer = jasmine.createSpy('MockCustomer');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Orderinfo': MockOrderinfo,
                'Status': MockStatus,
                'Customer': MockCustomer
            };
            createController = function() {
                $injector.get('$controller')("OrderinfoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'warehouseApp:orderinfoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
