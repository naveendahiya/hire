(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('career-portal-questionnaire-question', {
            parent: 'entity',
            url: '/career-portal-questionnaire-question',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CareerPortalQuestionnaireQuestions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/career-portal-questionnaire-question/career-portal-questionnaire-questions.html',
                    controller: 'CareerPortalQuestionnaireQuestionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('career-portal-questionnaire-question-detail', {
            parent: 'entity',
            url: '/career-portal-questionnaire-question/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CareerPortalQuestionnaireQuestion'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/career-portal-questionnaire-question/career-portal-questionnaire-question-detail.html',
                    controller: 'CareerPortalQuestionnaireQuestionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CareerPortalQuestionnaireQuestion', function($stateParams, CareerPortalQuestionnaireQuestion) {
                    return CareerPortalQuestionnaireQuestion.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'career-portal-questionnaire-question',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('career-portal-questionnaire-question-detail.edit', {
            parent: 'career-portal-questionnaire-question-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/career-portal-questionnaire-question/career-portal-questionnaire-question-dialog.html',
                    controller: 'CareerPortalQuestionnaireQuestionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CareerPortalQuestionnaireQuestion', function(CareerPortalQuestionnaireQuestion) {
                            return CareerPortalQuestionnaireQuestion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('career-portal-questionnaire-question.new', {
            parent: 'career-portal-questionnaire-question',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/career-portal-questionnaire-question/career-portal-questionnaire-question-dialog.html',
                    controller: 'CareerPortalQuestionnaireQuestionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                careerPortalQuestionnaireQuestionId: null,
                                careerPortalQuestionnaireId: null,
                                text: null,
                                minimumLength: null,
                                maximumLength: null,
                                requir: null,
                                position: null,
                                siteId: null,
                                type: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('career-portal-questionnaire-question', null, { reload: 'career-portal-questionnaire-question' });
                }, function() {
                    $state.go('career-portal-questionnaire-question');
                });
            }]
        })
        .state('career-portal-questionnaire-question.edit', {
            parent: 'career-portal-questionnaire-question',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/career-portal-questionnaire-question/career-portal-questionnaire-question-dialog.html',
                    controller: 'CareerPortalQuestionnaireQuestionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CareerPortalQuestionnaireQuestion', function(CareerPortalQuestionnaireQuestion) {
                            return CareerPortalQuestionnaireQuestion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('career-portal-questionnaire-question', null, { reload: 'career-portal-questionnaire-question' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('career-portal-questionnaire-question.delete', {
            parent: 'career-portal-questionnaire-question',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/career-portal-questionnaire-question/career-portal-questionnaire-question-delete-dialog.html',
                    controller: 'CareerPortalQuestionnaireQuestionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CareerPortalQuestionnaireQuestion', function(CareerPortalQuestionnaireQuestion) {
                            return CareerPortalQuestionnaireQuestion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('career-portal-questionnaire-question', null, { reload: 'career-portal-questionnaire-question' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
