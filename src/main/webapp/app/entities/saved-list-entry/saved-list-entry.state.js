(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('saved-list-entry', {
            parent: 'entity',
            url: '/saved-list-entry',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SavedListEntries'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/saved-list-entry/saved-list-entries.html',
                    controller: 'SavedListEntryController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('saved-list-entry-detail', {
            parent: 'entity',
            url: '/saved-list-entry/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SavedListEntry'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/saved-list-entry/saved-list-entry-detail.html',
                    controller: 'SavedListEntryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'SavedListEntry', function($stateParams, SavedListEntry) {
                    return SavedListEntry.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'saved-list-entry',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('saved-list-entry-detail.edit', {
            parent: 'saved-list-entry-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/saved-list-entry/saved-list-entry-dialog.html',
                    controller: 'SavedListEntryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SavedListEntry', function(SavedListEntry) {
                            return SavedListEntry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('saved-list-entry.new', {
            parent: 'saved-list-entry',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/saved-list-entry/saved-list-entry-dialog.html',
                    controller: 'SavedListEntryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                savedListEntryId: null,
                                savedListId: null,
                                dataItemType: null,
                                dataItemId: null,
                                siteId: null,
                                dateCreated: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('saved-list-entry', null, { reload: 'saved-list-entry' });
                }, function() {
                    $state.go('saved-list-entry');
                });
            }]
        })
        .state('saved-list-entry.edit', {
            parent: 'saved-list-entry',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/saved-list-entry/saved-list-entry-dialog.html',
                    controller: 'SavedListEntryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SavedListEntry', function(SavedListEntry) {
                            return SavedListEntry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('saved-list-entry', null, { reload: 'saved-list-entry' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('saved-list-entry.delete', {
            parent: 'saved-list-entry',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/saved-list-entry/saved-list-entry-delete-dialog.html',
                    controller: 'SavedListEntryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SavedListEntry', function(SavedListEntry) {
                            return SavedListEntry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('saved-list-entry', null, { reload: 'saved-list-entry' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
