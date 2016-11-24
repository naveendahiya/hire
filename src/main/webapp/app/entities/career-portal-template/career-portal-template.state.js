(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('career-portal-template', {
            parent: 'entity',
            url: '/career-portal-template',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CareerPortalTemplates'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/career-portal-template/career-portal-templates.html',
                    controller: 'CareerPortalTemplateController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('career-portal-template-detail', {
            parent: 'entity',
            url: '/career-portal-template/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CareerPortalTemplate'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/career-portal-template/career-portal-template-detail.html',
                    controller: 'CareerPortalTemplateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CareerPortalTemplate', function($stateParams, CareerPortalTemplate) {
                    return CareerPortalTemplate.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'career-portal-template',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('career-portal-template-detail.edit', {
            parent: 'career-portal-template-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/career-portal-template/career-portal-template-dialog.html',
                    controller: 'CareerPortalTemplateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CareerPortalTemplate', function(CareerPortalTemplate) {
                            return CareerPortalTemplate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('career-portal-template.new', {
            parent: 'career-portal-template',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/career-portal-template/career-portal-template-dialog.html',
                    controller: 'CareerPortalTemplateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                careerPortalTemplateId: null,
                                careerPortalName: null,
                                setting: null,
                                value: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('career-portal-template', null, { reload: 'career-portal-template' });
                }, function() {
                    $state.go('career-portal-template');
                });
            }]
        })
        .state('career-portal-template.edit', {
            parent: 'career-portal-template',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/career-portal-template/career-portal-template-dialog.html',
                    controller: 'CareerPortalTemplateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CareerPortalTemplate', function(CareerPortalTemplate) {
                            return CareerPortalTemplate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('career-portal-template', null, { reload: 'career-portal-template' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('career-portal-template.delete', {
            parent: 'career-portal-template',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/career-portal-template/career-portal-template-delete-dialog.html',
                    controller: 'CareerPortalTemplateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CareerPortalTemplate', function(CareerPortalTemplate) {
                            return CareerPortalTemplate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('career-portal-template', null, { reload: 'career-portal-template' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
