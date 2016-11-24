(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('career-portal-questionnaire', {
            parent: 'entity',
            url: '/career-portal-questionnaire',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CareerPortalQuestionnaires'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/career-portal-questionnaire/career-portal-questionnaires.html',
                    controller: 'CareerPortalQuestionnaireController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('career-portal-questionnaire-detail', {
            parent: 'entity',
            url: '/career-portal-questionnaire/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CareerPortalQuestionnaire'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/career-portal-questionnaire/career-portal-questionnaire-detail.html',
                    controller: 'CareerPortalQuestionnaireDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CareerPortalQuestionnaire', function($stateParams, CareerPortalQuestionnaire) {
                    return CareerPortalQuestionnaire.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'career-portal-questionnaire',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('career-portal-questionnaire-detail.edit', {
            parent: 'career-portal-questionnaire-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/career-portal-questionnaire/career-portal-questionnaire-dialog.html',
                    controller: 'CareerPortalQuestionnaireDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CareerPortalQuestionnaire', function(CareerPortalQuestionnaire) {
                            return CareerPortalQuestionnaire.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('career-portal-questionnaire.new', {
            parent: 'career-portal-questionnaire',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/career-portal-questionnaire/career-portal-questionnaire-dialog.html',
                    controller: 'CareerPortalQuestionnaireDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                careerPortalQuestionnaireId: null,
                                title: null,
                                siteId: null,
                                description: null,
                                isActive: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('career-portal-questionnaire', null, { reload: 'career-portal-questionnaire' });
                }, function() {
                    $state.go('career-portal-questionnaire');
                });
            }]
        })
        .state('career-portal-questionnaire.edit', {
            parent: 'career-portal-questionnaire',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/career-portal-questionnaire/career-portal-questionnaire-dialog.html',
                    controller: 'CareerPortalQuestionnaireDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CareerPortalQuestionnaire', function(CareerPortalQuestionnaire) {
                            return CareerPortalQuestionnaire.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('career-portal-questionnaire', null, { reload: 'career-portal-questionnaire' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('career-portal-questionnaire.delete', {
            parent: 'career-portal-questionnaire',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/career-portal-questionnaire/career-portal-questionnaire-delete-dialog.html',
                    controller: 'CareerPortalQuestionnaireDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CareerPortalQuestionnaire', function(CareerPortalQuestionnaire) {
                            return CareerPortalQuestionnaire.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('career-portal-questionnaire', null, { reload: 'career-portal-questionnaire' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
