(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('candidate-joborder-status', {
            parent: 'entity',
            url: '/candidate-joborder-status',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CandidateJoborderStatuses'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/candidate-joborder-status/candidate-joborder-statuses.html',
                    controller: 'CandidateJoborderStatusController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('candidate-joborder-status-detail', {
            parent: 'entity',
            url: '/candidate-joborder-status/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CandidateJoborderStatus'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/candidate-joborder-status/candidate-joborder-status-detail.html',
                    controller: 'CandidateJoborderStatusDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CandidateJoborderStatus', function($stateParams, CandidateJoborderStatus) {
                    return CandidateJoborderStatus.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'candidate-joborder-status',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('candidate-joborder-status-detail.edit', {
            parent: 'candidate-joborder-status-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/candidate-joborder-status/candidate-joborder-status-dialog.html',
                    controller: 'CandidateJoborderStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CandidateJoborderStatus', function(CandidateJoborderStatus) {
                            return CandidateJoborderStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('candidate-joborder-status.new', {
            parent: 'candidate-joborder-status',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/candidate-joborder-status/candidate-joborder-status-dialog.html',
                    controller: 'CandidateJoborderStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                candidateJoborderStatusId: null,
                                shortDescription: null,
                                canBeScheduled: null,
                                triggersEmail: null,
                                isEnabled: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('candidate-joborder-status', null, { reload: 'candidate-joborder-status' });
                }, function() {
                    $state.go('candidate-joborder-status');
                });
            }]
        })
        .state('candidate-joborder-status.edit', {
            parent: 'candidate-joborder-status',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/candidate-joborder-status/candidate-joborder-status-dialog.html',
                    controller: 'CandidateJoborderStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CandidateJoborderStatus', function(CandidateJoborderStatus) {
                            return CandidateJoborderStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('candidate-joborder-status', null, { reload: 'candidate-joborder-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('candidate-joborder-status.delete', {
            parent: 'candidate-joborder-status',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/candidate-joborder-status/candidate-joborder-status-delete-dialog.html',
                    controller: 'CandidateJoborderStatusDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CandidateJoborderStatus', function(CandidateJoborderStatus) {
                            return CandidateJoborderStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('candidate-joborder-status', null, { reload: 'candidate-joborder-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
