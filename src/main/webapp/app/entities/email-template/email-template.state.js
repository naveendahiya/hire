(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('email-template', {
            parent: 'entity',
            url: '/email-template',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'EmailTemplates'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/email-template/email-templates.html',
                    controller: 'EmailTemplateController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('email-template-detail', {
            parent: 'entity',
            url: '/email-template/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'EmailTemplate'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/email-template/email-template-detail.html',
                    controller: 'EmailTemplateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'EmailTemplate', function($stateParams, EmailTemplate) {
                    return EmailTemplate.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'email-template',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('email-template-detail.edit', {
            parent: 'email-template-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/email-template/email-template-dialog.html',
                    controller: 'EmailTemplateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EmailTemplate', function(EmailTemplate) {
                            return EmailTemplate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('email-template.new', {
            parent: 'email-template',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/email-template/email-template-dialog.html',
                    controller: 'EmailTemplateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                emailTemplateId: null,
                                text: null,
                                allowSubstitution: null,
                                siteId: null,
                                tag: null,
                                title: null,
                                possibleVariables: null,
                                disabled: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('email-template', null, { reload: 'email-template' });
                }, function() {
                    $state.go('email-template');
                });
            }]
        })
        .state('email-template.edit', {
            parent: 'email-template',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/email-template/email-template-dialog.html',
                    controller: 'EmailTemplateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EmailTemplate', function(EmailTemplate) {
                            return EmailTemplate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('email-template', null, { reload: 'email-template' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('email-template.delete', {
            parent: 'email-template',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/email-template/email-template-delete-dialog.html',
                    controller: 'EmailTemplateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EmailTemplate', function(EmailTemplate) {
                            return EmailTemplate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('email-template', null, { reload: 'email-template' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
