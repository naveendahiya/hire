(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('http-log-types', {
            parent: 'entity',
            url: '/http-log-types',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'HttpLogTypes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/http-log-types/http-log-types.html',
                    controller: 'HttpLogTypesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('http-log-types-detail', {
            parent: 'entity',
            url: '/http-log-types/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'HttpLogTypes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/http-log-types/http-log-types-detail.html',
                    controller: 'HttpLogTypesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'HttpLogTypes', function($stateParams, HttpLogTypes) {
                    return HttpLogTypes.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'http-log-types',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('http-log-types-detail.edit', {
            parent: 'http-log-types-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/http-log-types/http-log-types-dialog.html',
                    controller: 'HttpLogTypesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['HttpLogTypes', function(HttpLogTypes) {
                            return HttpLogTypes.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('http-log-types.new', {
            parent: 'http-log-types',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/http-log-types/http-log-types-dialog.html',
                    controller: 'HttpLogTypesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                logTypeId: null,
                                name: null,
                                description: null,
                                defaultLogType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('http-log-types', null, { reload: 'http-log-types' });
                }, function() {
                    $state.go('http-log-types');
                });
            }]
        })
        .state('http-log-types.edit', {
            parent: 'http-log-types',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/http-log-types/http-log-types-dialog.html',
                    controller: 'HttpLogTypesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['HttpLogTypes', function(HttpLogTypes) {
                            return HttpLogTypes.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('http-log-types', null, { reload: 'http-log-types' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('http-log-types.delete', {
            parent: 'http-log-types',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/http-log-types/http-log-types-delete-dialog.html',
                    controller: 'HttpLogTypesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['HttpLogTypes', function(HttpLogTypes) {
                            return HttpLogTypes.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('http-log-types', null, { reload: 'http-log-types' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
