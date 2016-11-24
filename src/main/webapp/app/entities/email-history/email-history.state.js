(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('email-history', {
            parent: 'entity',
            url: '/email-history',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'EmailHistories'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/email-history/email-histories.html',
                    controller: 'EmailHistoryController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('email-history-detail', {
            parent: 'entity',
            url: '/email-history/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'EmailHistory'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/email-history/email-history-detail.html',
                    controller: 'EmailHistoryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'EmailHistory', function($stateParams, EmailHistory) {
                    return EmailHistory.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'email-history',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('email-history-detail.edit', {
            parent: 'email-history-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/email-history/email-history-dialog.html',
                    controller: 'EmailHistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EmailHistory', function(EmailHistory) {
                            return EmailHistory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('email-history.new', {
            parent: 'email-history',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/email-history/email-history-dialog.html',
                    controller: 'EmailHistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                emailHistoryId: null,
                                fromAddress: null,
                                recipients: null,
                                text: null,
                                userId: null,
                                siteId: null,
                                date: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('email-history', null, { reload: 'email-history' });
                }, function() {
                    $state.go('email-history');
                });
            }]
        })
        .state('email-history.edit', {
            parent: 'email-history',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/email-history/email-history-dialog.html',
                    controller: 'EmailHistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EmailHistory', function(EmailHistory) {
                            return EmailHistory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('email-history', null, { reload: 'email-history' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('email-history.delete', {
            parent: 'email-history',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/email-history/email-history-delete-dialog.html',
                    controller: 'EmailHistoryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EmailHistory', function(EmailHistory) {
                            return EmailHistory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('email-history', null, { reload: 'email-history' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
