(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('module-schema', {
            parent: 'entity',
            url: '/module-schema',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ModuleSchemas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/module-schema/module-schemas.html',
                    controller: 'ModuleSchemaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('module-schema-detail', {
            parent: 'entity',
            url: '/module-schema/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ModuleSchema'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/module-schema/module-schema-detail.html',
                    controller: 'ModuleSchemaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ModuleSchema', function($stateParams, ModuleSchema) {
                    return ModuleSchema.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'module-schema',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('module-schema-detail.edit', {
            parent: 'module-schema-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/module-schema/module-schema-dialog.html',
                    controller: 'ModuleSchemaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ModuleSchema', function(ModuleSchema) {
                            return ModuleSchema.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('module-schema.new', {
            parent: 'module-schema',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/module-schema/module-schema-dialog.html',
                    controller: 'ModuleSchemaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                moduleSchemaId: null,
                                version: null,
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('module-schema', null, { reload: 'module-schema' });
                }, function() {
                    $state.go('module-schema');
                });
            }]
        })
        .state('module-schema.edit', {
            parent: 'module-schema',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/module-schema/module-schema-dialog.html',
                    controller: 'ModuleSchemaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ModuleSchema', function(ModuleSchema) {
                            return ModuleSchema.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('module-schema', null, { reload: 'module-schema' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('module-schema.delete', {
            parent: 'module-schema',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/module-schema/module-schema-delete-dialog.html',
                    controller: 'ModuleSchemaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ModuleSchema', function(ModuleSchema) {
                            return ModuleSchema.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('module-schema', null, { reload: 'module-schema' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
