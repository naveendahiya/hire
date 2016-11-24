(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('career-portal-questionnaire-answer', {
            parent: 'entity',
            url: '/career-portal-questionnaire-answer',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CareerPortalQuestionnaireAnswers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/career-portal-questionnaire-answer/career-portal-questionnaire-answers.html',
                    controller: 'CareerPortalQuestionnaireAnswerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('career-portal-questionnaire-answer-detail', {
            parent: 'entity',
            url: '/career-portal-questionnaire-answer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CareerPortalQuestionnaireAnswer'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/career-portal-questionnaire-answer/career-portal-questionnaire-answer-detail.html',
                    controller: 'CareerPortalQuestionnaireAnswerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CareerPortalQuestionnaireAnswer', function($stateParams, CareerPortalQuestionnaireAnswer) {
                    return CareerPortalQuestionnaireAnswer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'career-portal-questionnaire-answer',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('career-portal-questionnaire-answer-detail.edit', {
            parent: 'career-portal-questionnaire-answer-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/career-portal-questionnaire-answer/career-portal-questionnaire-answer-dialog.html',
                    controller: 'CareerPortalQuestionnaireAnswerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CareerPortalQuestionnaireAnswer', function(CareerPortalQuestionnaireAnswer) {
                            return CareerPortalQuestionnaireAnswer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('career-portal-questionnaire-answer.new', {
            parent: 'career-portal-questionnaire-answer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/career-portal-questionnaire-answer/career-portal-questionnaire-answer-dialog.html',
                    controller: 'CareerPortalQuestionnaireAnswerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                careerPortalQuestionnaireAnswerId: null,
                                careerPortalQuestionnaireQuestionId: null,
                                careerPortalQuestionnaireId: null,
                                text: null,
                                actionSource: null,
                                actionNotes: null,
                                actionIsHot: null,
                                actionIsActive: null,
                                actionCanRelocate: null,
                                actionKeySkills: null,
                                position: null,
                                siteId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('career-portal-questionnaire-answer', null, { reload: 'career-portal-questionnaire-answer' });
                }, function() {
                    $state.go('career-portal-questionnaire-answer');
                });
            }]
        })
        .state('career-portal-questionnaire-answer.edit', {
            parent: 'career-portal-questionnaire-answer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/career-portal-questionnaire-answer/career-portal-questionnaire-answer-dialog.html',
                    controller: 'CareerPortalQuestionnaireAnswerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CareerPortalQuestionnaireAnswer', function(CareerPortalQuestionnaireAnswer) {
                            return CareerPortalQuestionnaireAnswer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('career-portal-questionnaire-answer', null, { reload: 'career-portal-questionnaire-answer' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('career-portal-questionnaire-answer.delete', {
            parent: 'career-portal-questionnaire-answer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/career-portal-questionnaire-answer/career-portal-questionnaire-answer-delete-dialog.html',
                    controller: 'CareerPortalQuestionnaireAnswerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CareerPortalQuestionnaireAnswer', function(CareerPortalQuestionnaireAnswer) {
                            return CareerPortalQuestionnaireAnswer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('career-portal-questionnaire-answer', null, { reload: 'career-portal-questionnaire-answer' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
