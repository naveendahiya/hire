(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('candidate-tag', {
            parent: 'entity',
            url: '/candidate-tag',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CandidateTags'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/candidate-tag/candidate-tags.html',
                    controller: 'CandidateTagController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('candidate-tag-detail', {
            parent: 'entity',
            url: '/candidate-tag/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CandidateTag'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/candidate-tag/candidate-tag-detail.html',
                    controller: 'CandidateTagDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CandidateTag', function($stateParams, CandidateTag) {
                    return CandidateTag.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'candidate-tag',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('candidate-tag-detail.edit', {
            parent: 'candidate-tag-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/candidate-tag/candidate-tag-dialog.html',
                    controller: 'CandidateTagDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CandidateTag', function(CandidateTag) {
                            return CandidateTag.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('candidate-tag.new', {
            parent: 'candidate-tag',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/candidate-tag/candidate-tag-dialog.html',
                    controller: 'CandidateTagDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                siteId: null,
                                candidateId: null,
                                tagId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('candidate-tag', null, { reload: 'candidate-tag' });
                }, function() {
                    $state.go('candidate-tag');
                });
            }]
        })
        .state('candidate-tag.edit', {
            parent: 'candidate-tag',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/candidate-tag/candidate-tag-dialog.html',
                    controller: 'CandidateTagDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CandidateTag', function(CandidateTag) {
                            return CandidateTag.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('candidate-tag', null, { reload: 'candidate-tag' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('candidate-tag.delete', {
            parent: 'candidate-tag',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/candidate-tag/candidate-tag-delete-dialog.html',
                    controller: 'CandidateTagDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CandidateTag', function(CandidateTag) {
                            return CandidateTag.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('candidate-tag', null, { reload: 'candidate-tag' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
