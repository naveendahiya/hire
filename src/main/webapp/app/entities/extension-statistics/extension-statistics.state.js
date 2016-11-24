(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('extension-statistics', {
            parent: 'entity',
            url: '/extension-statistics',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ExtensionStatistics'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/extension-statistics/extension-statistics.html',
                    controller: 'ExtensionStatisticsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('extension-statistics-detail', {
            parent: 'entity',
            url: '/extension-statistics/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ExtensionStatistics'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/extension-statistics/extension-statistics-detail.html',
                    controller: 'ExtensionStatisticsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ExtensionStatistics', function($stateParams, ExtensionStatistics) {
                    return ExtensionStatistics.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'extension-statistics',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('extension-statistics-detail.edit', {
            parent: 'extension-statistics-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/extension-statistics/extension-statistics-dialog.html',
                    controller: 'ExtensionStatisticsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ExtensionStatistics', function(ExtensionStatistics) {
                            return ExtensionStatistics.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('extension-statistics.new', {
            parent: 'extension-statistics',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/extension-statistics/extension-statistics-dialog.html',
                    controller: 'ExtensionStatisticsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                extensionStatisticsId: null,
                                extension: null,
                                action: null,
                                user: null,
                                date: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('extension-statistics', null, { reload: 'extension-statistics' });
                }, function() {
                    $state.go('extension-statistics');
                });
            }]
        })
        .state('extension-statistics.edit', {
            parent: 'extension-statistics',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/extension-statistics/extension-statistics-dialog.html',
                    controller: 'ExtensionStatisticsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ExtensionStatistics', function(ExtensionStatistics) {
                            return ExtensionStatistics.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('extension-statistics', null, { reload: 'extension-statistics' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('extension-statistics.delete', {
            parent: 'extension-statistics',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/extension-statistics/extension-statistics-delete-dialog.html',
                    controller: 'ExtensionStatisticsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ExtensionStatistics', function(ExtensionStatistics) {
                            return ExtensionStatistics.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('extension-statistics', null, { reload: 'extension-statistics' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
