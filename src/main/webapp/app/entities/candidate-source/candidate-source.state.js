(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('candidate-source', {
            parent: 'entity',
            url: '/candidate-source',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CandidateSources'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/candidate-source/candidate-sources.html',
                    controller: 'CandidateSourceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('candidate-source-detail', {
            parent: 'entity',
            url: '/candidate-source/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CandidateSource'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/candidate-source/candidate-source-detail.html',
                    controller: 'CandidateSourceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CandidateSource', function($stateParams, CandidateSource) {
                    return CandidateSource.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'candidate-source',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('candidate-source-detail.edit', {
            parent: 'candidate-source-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/candidate-source/candidate-source-dialog.html',
                    controller: 'CandidateSourceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CandidateSource', function(CandidateSource) {
                            return CandidateSource.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('candidate-source.new', {
            parent: 'candidate-source',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/candidate-source/candidate-source-dialog.html',
                    controller: 'CandidateSourceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                sourceId: null,
                                name: null,
                                siteId: null,
                                dateCreated: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('candidate-source', null, { reload: 'candidate-source' });
                }, function() {
                    $state.go('candidate-source');
                });
            }]
        })
        .state('candidate-source.edit', {
            parent: 'candidate-source',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/candidate-source/candidate-source-dialog.html',
                    controller: 'CandidateSourceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CandidateSource', function(CandidateSource) {
                            return CandidateSource.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('candidate-source', null, { reload: 'candidate-source' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('candidate-source.delete', {
            parent: 'candidate-source',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/candidate-source/candidate-source-delete-dialog.html',
                    controller: 'CandidateSourceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CandidateSource', function(CandidateSource) {
                            return CandidateSource.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('candidate-source', null, { reload: 'candidate-source' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
