(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('company-department', {
            parent: 'entity',
            url: '/company-department',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CompanyDepartments'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/company-department/company-departments.html',
                    controller: 'CompanyDepartmentController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('company-department-detail', {
            parent: 'entity',
            url: '/company-department/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CompanyDepartment'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/company-department/company-department-detail.html',
                    controller: 'CompanyDepartmentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CompanyDepartment', function($stateParams, CompanyDepartment) {
                    return CompanyDepartment.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'company-department',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('company-department-detail.edit', {
            parent: 'company-department-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/company-department/company-department-dialog.html',
                    controller: 'CompanyDepartmentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CompanyDepartment', function(CompanyDepartment) {
                            return CompanyDepartment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('company-department.new', {
            parent: 'company-department',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/company-department/company-department-dialog.html',
                    controller: 'CompanyDepartmentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                companyDepartmentId: null,
                                name: null,
                                companyId: null,
                                siteId: null,
                                dateCreated: null,
                                createdBy: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('company-department', null, { reload: 'company-department' });
                }, function() {
                    $state.go('company-department');
                });
            }]
        })
        .state('company-department.edit', {
            parent: 'company-department',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/company-department/company-department-dialog.html',
                    controller: 'CompanyDepartmentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CompanyDepartment', function(CompanyDepartment) {
                            return CompanyDepartment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('company-department', null, { reload: 'company-department' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('company-department.delete', {
            parent: 'company-department',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/company-department/company-department-delete-dialog.html',
                    controller: 'CompanyDepartmentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CompanyDepartment', function(CompanyDepartment) {
                            return CompanyDepartment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('company-department', null, { reload: 'company-department' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
