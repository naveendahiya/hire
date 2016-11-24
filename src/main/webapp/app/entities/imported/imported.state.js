(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('imported', {
            parent: 'entity',
            url: '/imported',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Importeds'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/imported/importeds.html',
                    controller: 'ImportedController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('imported-detail', {
            parent: 'entity',
            url: '/imported/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Imported'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/imported/imported-detail.html',
                    controller: 'ImportedDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Imported', function($stateParams, Imported) {
                    return Imported.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'imported',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('imported-detail.edit', {
            parent: 'imported-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/imported/imported-dialog.html',
                    controller: 'ImportedDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Imported', function(Imported) {
                            return Imported.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('imported.new', {
            parent: 'imported',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/imported/imported-dialog.html',
                    controller: 'ImportedDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                importedId: null,
                                moduleName: null,
                                reverted: null,
                                siteId: null,
                                importedErrors: null,
                                addedLines: null,
                                dateCreated: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('imported', null, { reload: 'imported' });
                }, function() {
                    $state.go('imported');
                });
            }]
        })
        .state('imported.edit', {
            parent: 'imported',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/imported/imported-dialog.html',
                    controller: 'ImportedDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Imported', function(Imported) {
                            return Imported.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('imported', null, { reload: 'imported' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('imported.delete', {
            parent: 'imported',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/imported/imported-delete-dialog.html',
                    controller: 'ImportedDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Imported', function(Imported) {
                            return Imported.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('imported', null, { reload: 'imported' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
