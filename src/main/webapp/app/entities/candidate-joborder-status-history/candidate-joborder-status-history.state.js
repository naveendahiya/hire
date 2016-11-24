(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('candidate-joborder-status-history', {
            parent: 'entity',
            url: '/candidate-joborder-status-history',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CandidateJoborderStatusHistories'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/candidate-joborder-status-history/candidate-joborder-status-histories.html',
                    controller: 'CandidateJoborderStatusHistoryController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('candidate-joborder-status-history-detail', {
            parent: 'entity',
            url: '/candidate-joborder-status-history/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CandidateJoborderStatusHistory'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/candidate-joborder-status-history/candidate-joborder-status-history-detail.html',
                    controller: 'CandidateJoborderStatusHistoryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CandidateJoborderStatusHistory', function($stateParams, CandidateJoborderStatusHistory) {
                    return CandidateJoborderStatusHistory.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'candidate-joborder-status-history',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('candidate-joborder-status-history-detail.edit', {
            parent: 'candidate-joborder-status-history-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/candidate-joborder-status-history/candidate-joborder-status-history-dialog.html',
                    controller: 'CandidateJoborderStatusHistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CandidateJoborderStatusHistory', function(CandidateJoborderStatusHistory) {
                            return CandidateJoborderStatusHistory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('candidate-joborder-status-history.new', {
            parent: 'candidate-joborder-status-history',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/candidate-joborder-status-history/candidate-joborder-status-history-dialog.html',
                    controller: 'CandidateJoborderStatusHistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                candidateJoborderStatusHistoryId: null,
                                candidateId: null,
                                joborderId: null,
                                date: null,
                                statusFrom: null,
                                statusTo: null,
                                siteId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('candidate-joborder-status-history', null, { reload: 'candidate-joborder-status-history' });
                }, function() {
                    $state.go('candidate-joborder-status-history');
                });
            }]
        })
        .state('candidate-joborder-status-history.edit', {
            parent: 'candidate-joborder-status-history',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/candidate-joborder-status-history/candidate-joborder-status-history-dialog.html',
                    controller: 'CandidateJoborderStatusHistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CandidateJoborderStatusHistory', function(CandidateJoborderStatusHistory) {
                            return CandidateJoborderStatusHistory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('candidate-joborder-status-history', null, { reload: 'candidate-joborder-status-history' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('candidate-joborder-status-history.delete', {
            parent: 'candidate-joborder-status-history',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/candidate-joborder-status-history/candidate-joborder-status-history-delete-dialog.html',
                    controller: 'CandidateJoborderStatusHistoryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CandidateJoborderStatusHistory', function(CandidateJoborderStatusHistory) {
                            return CandidateJoborderStatusHistory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('candidate-joborder-status-history', null, { reload: 'candidate-joborder-status-history' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
