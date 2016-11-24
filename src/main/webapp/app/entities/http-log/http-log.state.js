(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('http-log', {
            parent: 'entity',
            url: '/http-log',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'HttpLogs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/http-log/http-logs.html',
                    controller: 'HttpLogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('http-log-detail', {
            parent: 'entity',
            url: '/http-log/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'HttpLog'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/http-log/http-log-detail.html',
                    controller: 'HttpLogDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'HttpLog', function($stateParams, HttpLog) {
                    return HttpLog.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'http-log',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('http-log-detail.edit', {
            parent: 'http-log-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/http-log/http-log-dialog.html',
                    controller: 'HttpLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['HttpLog', function(HttpLog) {
                            return HttpLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('http-log.new', {
            parent: 'http-log',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/http-log/http-log-dialog.html',
                    controller: 'HttpLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                logId: null,
                                siteId: null,
                                remoteAddr: null,
                                httpUserAgent: null,
                                scriptFilename: null,
                                requestMethod: null,
                                queryString: null,
                                requestUri: null,
                                scriptName: null,
                                logType: null,
                                date: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('http-log', null, { reload: 'http-log' });
                }, function() {
                    $state.go('http-log');
                });
            }]
        })
        .state('http-log.edit', {
            parent: 'http-log',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/http-log/http-log-dialog.html',
                    controller: 'HttpLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['HttpLog', function(HttpLog) {
                            return HttpLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('http-log', null, { reload: 'http-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('http-log.delete', {
            parent: 'http-log',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/http-log/http-log-delete-dialog.html',
                    controller: 'HttpLogDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['HttpLog', function(HttpLog) {
                            return HttpLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('http-log', null, { reload: 'http-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
