(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('joborder', {
            parent: 'entity',
            url: '/joborder',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Joborders'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/joborder/joborders.html',
                    controller: 'JoborderController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('joborder-detail', {
            parent: 'entity',
            url: '/joborder/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Joborder'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/joborder/joborder-detail.html',
                    controller: 'JoborderDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Joborder', function($stateParams, Joborder) {
                    return Joborder.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'joborder',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('joborder-detail.edit', {
            parent: 'joborder-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/joborder/joborder-dialog.html',
                    controller: 'JoborderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Joborder', function(Joborder) {
                            return Joborder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('joborder.new', {
            parent: 'joborder',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/joborder/joborder-dialog.html',
                    controller: 'JoborderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                joborderId: null,
                                recruiter: null,
                                contactId: null,
                                companyId: null,
                                enteredBy: null,
                                owner: null,
                                siteId: null,
                                clientJobId: null,
                                title: null,
                                description: null,
                                notes: null,
                                type: null,
                                duration: null,
                                rateMax: null,
                                salary: null,
                                status: null,
                                isHot: null,
                                openings: null,
                                city: null,
                                state: null,
                                startDate: null,
                                dateCreated: null,
                                dateModified: null,
                                publicV: null,
                                companyDepartmentId: null,
                                isAdminHidden: null,
                                openingsAvailable: null,
                                questionnaireId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('joborder', null, { reload: 'joborder' });
                }, function() {
                    $state.go('joborder');
                });
            }]
        })
        .state('joborder.edit', {
            parent: 'joborder',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/joborder/joborder-dialog.html',
                    controller: 'JoborderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Joborder', function(Joborder) {
                            return Joborder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('joborder', null, { reload: 'joborder' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('joborder.delete', {
            parent: 'joborder',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/joborder/joborder-delete-dialog.html',
                    controller: 'JoborderDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Joborder', function(Joborder) {
                            return Joborder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('joborder', null, { reload: 'joborder' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
