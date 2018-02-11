'use strict';

angular.module('warehouseApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('orderinfo', {
                parent: 'entity',
                url: '/orderinfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'warehouseApp.orderinfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/orderinfo/orderinfos.html',
                        controller: 'OrderinfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('orderinfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('orderinfo.detail', {
                parent: 'entity',
                url: '/orderinfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'warehouseApp.orderinfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/orderinfo/orderinfo-detail.html',
                        controller: 'OrderinfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('orderinfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Orderinfo', function($stateParams, Orderinfo) {
                        return Orderinfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('orderinfo.new', {
                parent: 'orderinfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/orderinfo/orderinfo-dialog.html',
                        controller: 'OrderinfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    datecreate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('orderinfo', null, { reload: true });
                    }, function() {
                        $state.go('orderinfo');
                    })
                }]
            })
            .state('orderinfo.edit', {
                parent: 'orderinfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/orderinfo/orderinfo-dialog.html',
                        controller: 'OrderinfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Orderinfo', function(Orderinfo) {
                                return Orderinfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('orderinfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('orderinfo.delete', {
                parent: 'orderinfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/orderinfo/orderinfo-delete-dialog.html',
                        controller: 'OrderinfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Orderinfo', function(Orderinfo) {
                                return Orderinfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('orderinfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
