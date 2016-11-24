(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('eeo-veteran-type', {
            parent: 'entity',
            url: '/eeo-veteran-type',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'EeoVeteranTypes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/eeo-veteran-type/eeo-veteran-types.html',
                    controller: 'EeoVeteranTypeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('eeo-veteran-type-detail', {
            parent: 'entity',
            url: '/eeo-veteran-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'EeoVeteranType'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/eeo-veteran-type/eeo-veteran-type-detail.html',
                    controller: 'EeoVeteranTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'EeoVeteranType', function($stateParams, EeoVeteranType) {
                    return EeoVeteranType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'eeo-veteran-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('eeo-veteran-type-detail.edit', {
            parent: 'eeo-veteran-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/eeo-veteran-type/eeo-veteran-type-dialog.html',
                    controller: 'EeoVeteranTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EeoVeteranType', function(EeoVeteranType) {
                            return EeoVeteranType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('eeo-veteran-type.new', {
            parent: 'eeo-veteran-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/eeo-veteran-type/eeo-veteran-type-dialog.html',
                    controller: 'EeoVeteranTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                eeoVeteranTypeId: null,
                                type: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('eeo-veteran-type', null, { reload: 'eeo-veteran-type' });
                }, function() {
                    $state.go('eeo-veteran-type');
                });
            }]
        })
        .state('eeo-veteran-type.edit', {
            parent: 'eeo-veteran-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/eeo-veteran-type/eeo-veteran-type-dialog.html',
                    controller: 'EeoVeteranTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EeoVeteranType', function(EeoVeteranType) {
                            return EeoVeteranType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('eeo-veteran-type', null, { reload: 'eeo-veteran-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('eeo-veteran-type.delete', {
            parent: 'eeo-veteran-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/eeo-veteran-type/eeo-veteran-type-delete-dialog.html',
                    controller: 'EeoVeteranTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EeoVeteranType', function(EeoVeteranType) {
                            return EeoVeteranType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('eeo-veteran-type', null, { reload: 'eeo-veteran-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
