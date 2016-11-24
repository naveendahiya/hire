(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('candidate-joborder', {
            parent: 'entity',
            url: '/candidate-joborder',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CandidateJoborders'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/candidate-joborder/candidate-joborders.html',
                    controller: 'CandidateJoborderController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('candidate-joborder-detail', {
            parent: 'entity',
            url: '/candidate-joborder/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CandidateJoborder'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/candidate-joborder/candidate-joborder-detail.html',
                    controller: 'CandidateJoborderDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CandidateJoborder', function($stateParams, CandidateJoborder) {
                    return CandidateJoborder.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'candidate-joborder',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('candidate-joborder-detail.edit', {
            parent: 'candidate-joborder-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/candidate-joborder/candidate-joborder-dialog.html',
                    controller: 'CandidateJoborderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CandidateJoborder', function(CandidateJoborder) {
                            return CandidateJoborder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('candidate-joborder.new', {
            parent: 'candidate-joborder',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/candidate-joborder/candidate-joborder-dialog.html',
                    controller: 'CandidateJoborderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                candidateJoborderId: null,
                                candidateId: null,
                                joborderId: null,
                                siteId: null,
                                status: null,
                                dateSubmitted: null,
                                dateCreated: null,
                                dateModified: null,
                                ratingValue: null,
                                addedBy: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('candidate-joborder', null, { reload: 'candidate-joborder' });
                }, function() {
                    $state.go('candidate-joborder');
                });
            }]
        })
        .state('candidate-joborder.edit', {
            parent: 'candidate-joborder',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/candidate-joborder/candidate-joborder-dialog.html',
                    controller: 'CandidateJoborderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CandidateJoborder', function(CandidateJoborder) {
                            return CandidateJoborder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('candidate-joborder', null, { reload: 'candidate-joborder' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('candidate-joborder.delete', {
            parent: 'candidate-joborder',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/candidate-joborder/candidate-joborder-delete-dialog.html',
                    controller: 'CandidateJoborderDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CandidateJoborder', function(CandidateJoborder) {
                            return CandidateJoborder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('candidate-joborder', null, { reload: 'candidate-joborder' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
