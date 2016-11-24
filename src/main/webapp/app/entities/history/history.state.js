(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('history', {
            parent: 'entity',
            url: '/history',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Histories'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/history/histories.html',
                    controller: 'HistoryController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('history-detail', {
            parent: 'entity',
            url: '/history/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'History'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/history/history-detail.html',
                    controller: 'HistoryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'History', function($stateParams, History) {
                    return History.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'history',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('history-detail.edit', {
            parent: 'history-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/history/history-dialog.html',
                    controller: 'HistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['History', function(History) {
                            return History.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('history.new', {
            parent: 'history',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/history/history-dialog.html',
                    controller: 'HistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                historyId: null,
                                dataItemType: null,
                                dataItemId: null,
                                theField: null,
                                previousValue: null,
                                newValue: null,
                                description: null,
                                setDate: null,
                                enteredBy: null,
                                siteId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('history', null, { reload: 'history' });
                }, function() {
                    $state.go('history');
                });
            }]
        })
        .state('history.edit', {
            parent: 'history',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/history/history-dialog.html',
                    controller: 'HistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['History', function(History) {
                            return History.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('history', null, { reload: 'history' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('history.delete', {
            parent: 'history',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/history/history-delete-dialog.html',
                    controller: 'HistoryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['History', function(History) {
                            return History.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('history', null, { reload: 'history' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
