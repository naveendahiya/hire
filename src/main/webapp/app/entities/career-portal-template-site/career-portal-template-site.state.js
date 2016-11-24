(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('career-portal-template-site', {
            parent: 'entity',
            url: '/career-portal-template-site',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CareerPortalTemplateSites'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/career-portal-template-site/career-portal-template-sites.html',
                    controller: 'CareerPortalTemplateSiteController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('career-portal-template-site-detail', {
            parent: 'entity',
            url: '/career-portal-template-site/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CareerPortalTemplateSite'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/career-portal-template-site/career-portal-template-site-detail.html',
                    controller: 'CareerPortalTemplateSiteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CareerPortalTemplateSite', function($stateParams, CareerPortalTemplateSite) {
                    return CareerPortalTemplateSite.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'career-portal-template-site',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('career-portal-template-site-detail.edit', {
            parent: 'career-portal-template-site-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/career-portal-template-site/career-portal-template-site-dialog.html',
                    controller: 'CareerPortalTemplateSiteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CareerPortalTemplateSite', function(CareerPortalTemplateSite) {
                            return CareerPortalTemplateSite.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('career-portal-template-site.new', {
            parent: 'career-portal-template-site',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/career-portal-template-site/career-portal-template-site-dialog.html',
                    controller: 'CareerPortalTemplateSiteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                careerPortalTemplateId: null,
                                careerPortalName: null,
                                siteId: null,
                                setting: null,
                                value: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('career-portal-template-site', null, { reload: 'career-portal-template-site' });
                }, function() {
                    $state.go('career-portal-template-site');
                });
            }]
        })
        .state('career-portal-template-site.edit', {
            parent: 'career-portal-template-site',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/career-portal-template-site/career-portal-template-site-dialog.html',
                    controller: 'CareerPortalTemplateSiteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CareerPortalTemplateSite', function(CareerPortalTemplateSite) {
                            return CareerPortalTemplateSite.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('career-portal-template-site', null, { reload: 'career-portal-template-site' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('career-portal-template-site.delete', {
            parent: 'career-portal-template-site',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/career-portal-template-site/career-portal-template-site-delete-dialog.html',
                    controller: 'CareerPortalTemplateSiteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CareerPortalTemplateSite', function(CareerPortalTemplateSite) {
                            return CareerPortalTemplateSite.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('career-portal-template-site', null, { reload: 'career-portal-template-site' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
