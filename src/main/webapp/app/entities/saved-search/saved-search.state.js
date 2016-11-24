(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('saved-search', {
            parent: 'entity',
            url: '/saved-search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SavedSearches'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/saved-search/saved-searches.html',
                    controller: 'SavedSearchController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('saved-search-detail', {
            parent: 'entity',
            url: '/saved-search/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SavedSearch'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/saved-search/saved-search-detail.html',
                    controller: 'SavedSearchDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'SavedSearch', function($stateParams, SavedSearch) {
                    return SavedSearch.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'saved-search',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('saved-search-detail.edit', {
            parent: 'saved-search-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/saved-search/saved-search-dialog.html',
                    controller: 'SavedSearchDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SavedSearch', function(SavedSearch) {
                            return SavedSearch.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('saved-search.new', {
            parent: 'saved-search',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/saved-search/saved-search-dialog.html',
                    controller: 'SavedSearchDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                searchId: null,
                                dataItemText: null,
                                url: null,
                                isCustom: null,
                                dataItemType: null,
                                userId: null,
                                siteId: null,
                                dateCreated: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('saved-search', null, { reload: 'saved-search' });
                }, function() {
                    $state.go('saved-search');
                });
            }]
        })
        .state('saved-search.edit', {
            parent: 'saved-search',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/saved-search/saved-search-dialog.html',
                    controller: 'SavedSearchDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SavedSearch', function(SavedSearch) {
                            return SavedSearch.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('saved-search', null, { reload: 'saved-search' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('saved-search.delete', {
            parent: 'saved-search',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/saved-search/saved-search-delete-dialog.html',
                    controller: 'SavedSearchDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SavedSearch', function(SavedSearch) {
                            return SavedSearch.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('saved-search', null, { reload: 'saved-search' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
