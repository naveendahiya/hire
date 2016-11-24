(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('eeo-ethnic-type', {
            parent: 'entity',
            url: '/eeo-ethnic-type',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'EeoEthnicTypes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/eeo-ethnic-type/eeo-ethnic-types.html',
                    controller: 'EeoEthnicTypeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('eeo-ethnic-type-detail', {
            parent: 'entity',
            url: '/eeo-ethnic-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'EeoEthnicType'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/eeo-ethnic-type/eeo-ethnic-type-detail.html',
                    controller: 'EeoEthnicTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'EeoEthnicType', function($stateParams, EeoEthnicType) {
                    return EeoEthnicType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'eeo-ethnic-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('eeo-ethnic-type-detail.edit', {
            parent: 'eeo-ethnic-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/eeo-ethnic-type/eeo-ethnic-type-dialog.html',
                    controller: 'EeoEthnicTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EeoEthnicType', function(EeoEthnicType) {
                            return EeoEthnicType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('eeo-ethnic-type.new', {
            parent: 'eeo-ethnic-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/eeo-ethnic-type/eeo-ethnic-type-dialog.html',
                    controller: 'EeoEthnicTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                eeoEthnicTypeId: null,
                                type: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('eeo-ethnic-type', null, { reload: 'eeo-ethnic-type' });
                }, function() {
                    $state.go('eeo-ethnic-type');
                });
            }]
        })
        .state('eeo-ethnic-type.edit', {
            parent: 'eeo-ethnic-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/eeo-ethnic-type/eeo-ethnic-type-dialog.html',
                    controller: 'EeoEthnicTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EeoEthnicType', function(EeoEthnicType) {
                            return EeoEthnicType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('eeo-ethnic-type', null, { reload: 'eeo-ethnic-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('eeo-ethnic-type.delete', {
            parent: 'eeo-ethnic-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/eeo-ethnic-type/eeo-ethnic-type-delete-dialog.html',
                    controller: 'EeoEthnicTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EeoEthnicType', function(EeoEthnicType) {
                            return EeoEthnicType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('eeo-ethnic-type', null, { reload: 'eeo-ethnic-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
