(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('candidate-jobordrer-status-type', {
            parent: 'entity',
            url: '/candidate-jobordrer-status-type',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CandidateJobordrerStatusTypes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/candidate-jobordrer-status-type/candidate-jobordrer-status-types.html',
                    controller: 'CandidateJobordrerStatusTypeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('candidate-jobordrer-status-type-detail', {
            parent: 'entity',
            url: '/candidate-jobordrer-status-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CandidateJobordrerStatusType'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/candidate-jobordrer-status-type/candidate-jobordrer-status-type-detail.html',
                    controller: 'CandidateJobordrerStatusTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CandidateJobordrerStatusType', function($stateParams, CandidateJobordrerStatusType) {
                    return CandidateJobordrerStatusType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'candidate-jobordrer-status-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('candidate-jobordrer-status-type-detail.edit', {
            parent: 'candidate-jobordrer-status-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/candidate-jobordrer-status-type/candidate-jobordrer-status-type-dialog.html',
                    controller: 'CandidateJobordrerStatusTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CandidateJobordrerStatusType', function(CandidateJobordrerStatusType) {
                            return CandidateJobordrerStatusType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('candidate-jobordrer-status-type.new', {
            parent: 'candidate-jobordrer-status-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/candidate-jobordrer-status-type/candidate-jobordrer-status-type-dialog.html',
                    controller: 'CandidateJobordrerStatusTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                candidateStatusTypeId: null,
                                shortDescription: null,
                                canBeScheduled: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('candidate-jobordrer-status-type', null, { reload: 'candidate-jobordrer-status-type' });
                }, function() {
                    $state.go('candidate-jobordrer-status-type');
                });
            }]
        })
        .state('candidate-jobordrer-status-type.edit', {
            parent: 'candidate-jobordrer-status-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/candidate-jobordrer-status-type/candidate-jobordrer-status-type-dialog.html',
                    controller: 'CandidateJobordrerStatusTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CandidateJobordrerStatusType', function(CandidateJobordrerStatusType) {
                            return CandidateJobordrerStatusType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('candidate-jobordrer-status-type', null, { reload: 'candidate-jobordrer-status-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('candidate-jobordrer-status-type.delete', {
            parent: 'candidate-jobordrer-status-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/candidate-jobordrer-status-type/candidate-jobordrer-status-type-delete-dialog.html',
                    controller: 'CandidateJobordrerStatusTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CandidateJobordrerStatusType', function(CandidateJobordrerStatusType) {
                            return CandidateJobordrerStatusType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('candidate-jobordrer-status-type', null, { reload: 'candidate-jobordrer-status-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
