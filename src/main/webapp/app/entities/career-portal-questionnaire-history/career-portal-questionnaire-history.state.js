(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('career-portal-questionnaire-history', {
            parent: 'entity',
            url: '/career-portal-questionnaire-history',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CareerPortalQuestionnaireHistories'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/career-portal-questionnaire-history/career-portal-questionnaire-histories.html',
                    controller: 'CareerPortalQuestionnaireHistoryController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('career-portal-questionnaire-history-detail', {
            parent: 'entity',
            url: '/career-portal-questionnaire-history/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CareerPortalQuestionnaireHistory'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/career-portal-questionnaire-history/career-portal-questionnaire-history-detail.html',
                    controller: 'CareerPortalQuestionnaireHistoryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CareerPortalQuestionnaireHistory', function($stateParams, CareerPortalQuestionnaireHistory) {
                    return CareerPortalQuestionnaireHistory.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'career-portal-questionnaire-history',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('career-portal-questionnaire-history-detail.edit', {
            parent: 'career-portal-questionnaire-history-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/career-portal-questionnaire-history/career-portal-questionnaire-history-dialog.html',
                    controller: 'CareerPortalQuestionnaireHistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CareerPortalQuestionnaireHistory', function(CareerPortalQuestionnaireHistory) {
                            return CareerPortalQuestionnaireHistory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('career-portal-questionnaire-history.new', {
            parent: 'career-portal-questionnaire-history',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/career-portal-questionnaire-history/career-portal-questionnaire-history-dialog.html',
                    controller: 'CareerPortalQuestionnaireHistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                careerPortalQuestionnaireHistoryId: null,
                                siteId: null,
                                candidateId: null,
                                question: null,
                                answer: null,
                                questionnaireTitle: null,
                                questionnaireDescription: null,
                                date: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('career-portal-questionnaire-history', null, { reload: 'career-portal-questionnaire-history' });
                }, function() {
                    $state.go('career-portal-questionnaire-history');
                });
            }]
        })
        .state('career-portal-questionnaire-history.edit', {
            parent: 'career-portal-questionnaire-history',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/career-portal-questionnaire-history/career-portal-questionnaire-history-dialog.html',
                    controller: 'CareerPortalQuestionnaireHistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CareerPortalQuestionnaireHistory', function(CareerPortalQuestionnaireHistory) {
                            return CareerPortalQuestionnaireHistory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('career-portal-questionnaire-history', null, { reload: 'career-portal-questionnaire-history' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('career-portal-questionnaire-history.delete', {
            parent: 'career-portal-questionnaire-history',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/career-portal-questionnaire-history/career-portal-questionnaire-history-delete-dialog.html',
                    controller: 'CareerPortalQuestionnaireHistoryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CareerPortalQuestionnaireHistory', function(CareerPortalQuestionnaireHistory) {
                            return CareerPortalQuestionnaireHistory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('career-portal-questionnaire-history', null, { reload: 'career-portal-questionnaire-history' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
