(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('data-item-type', {
            parent: 'entity',
            url: '/data-item-type',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'DataItemTypes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/data-item-type/data-item-types.html',
                    controller: 'DataItemTypeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('data-item-type-detail', {
            parent: 'entity',
            url: '/data-item-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'DataItemType'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/data-item-type/data-item-type-detail.html',
                    controller: 'DataItemTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'DataItemType', function($stateParams, DataItemType) {
                    return DataItemType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'data-item-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('data-item-type-detail.edit', {
            parent: 'data-item-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/data-item-type/data-item-type-dialog.html',
                    controller: 'DataItemTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DataItemType', function(DataItemType) {
                            return DataItemType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('data-item-type.new', {
            parent: 'data-item-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/data-item-type/data-item-type-dialog.html',
                    controller: 'DataItemTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                dataItemTypeId: null,
                                shortDescription: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('data-item-type', null, { reload: 'data-item-type' });
                }, function() {
                    $state.go('data-item-type');
                });
            }]
        })
        .state('data-item-type.edit', {
            parent: 'data-item-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/data-item-type/data-item-type-dialog.html',
                    controller: 'DataItemTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DataItemType', function(DataItemType) {
                            return DataItemType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('data-item-type', null, { reload: 'data-item-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('data-item-type.delete', {
            parent: 'data-item-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/data-item-type/data-item-type-delete-dialog.html',
                    controller: 'DataItemTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DataItemType', function(DataItemType) {
                            return DataItemType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('data-item-type', null, { reload: 'data-item-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
