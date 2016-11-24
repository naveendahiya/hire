(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('saved-list', {
            parent: 'entity',
            url: '/saved-list',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SavedLists'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/saved-list/saved-lists.html',
                    controller: 'SavedListController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('saved-list-detail', {
            parent: 'entity',
            url: '/saved-list/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SavedList'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/saved-list/saved-list-detail.html',
                    controller: 'SavedListDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'SavedList', function($stateParams, SavedList) {
                    return SavedList.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'saved-list',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('saved-list-detail.edit', {
            parent: 'saved-list-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/saved-list/saved-list-dialog.html',
                    controller: 'SavedListDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SavedList', function(SavedList) {
                            return SavedList.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('saved-list.new', {
            parent: 'saved-list',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/saved-list/saved-list-dialog.html',
                    controller: 'SavedListDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                savedListId: null,
                                description: null,
                                dataItemType: null,
                                siteId: null,
                                isDynamic: null,
                                datagridInstance: null,
                                parameters: null,
                                createdBy: null,
                                numberEntries: null,
                                dateCreated: null,
                                dateModified: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('saved-list', null, { reload: 'saved-list' });
                }, function() {
                    $state.go('saved-list');
                });
            }]
        })
        .state('saved-list.edit', {
            parent: 'saved-list',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/saved-list/saved-list-dialog.html',
                    controller: 'SavedListDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SavedList', function(SavedList) {
                            return SavedList.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('saved-list', null, { reload: 'saved-list' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('saved-list.delete', {
            parent: 'saved-list',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/saved-list/saved-list-delete-dialog.html',
                    controller: 'SavedListDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SavedList', function(SavedList) {
                            return SavedList.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('saved-list', null, { reload: 'saved-list' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
