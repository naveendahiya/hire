(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('activity-type', {
            parent: 'entity',
            url: '/activity-type',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ActivityTypes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/activity-type/activity-types.html',
                    controller: 'ActivityTypeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('activity-type-detail', {
            parent: 'entity',
            url: '/activity-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ActivityType'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/activity-type/activity-type-detail.html',
                    controller: 'ActivityTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ActivityType', function($stateParams, ActivityType) {
                    return ActivityType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'activity-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('activity-type-detail.edit', {
            parent: 'activity-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/activity-type/activity-type-dialog.html',
                    controller: 'ActivityTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ActivityType', function(ActivityType) {
                            return ActivityType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('activity-type.new', {
            parent: 'activity-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/activity-type/activity-type-dialog.html',
                    controller: 'ActivityTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                activityTypeId: null,
                                shortDescription: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('activity-type', null, { reload: 'activity-type' });
                }, function() {
                    $state.go('activity-type');
                });
            }]
        })
        .state('activity-type.edit', {
            parent: 'activity-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/activity-type/activity-type-dialog.html',
                    controller: 'ActivityTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ActivityType', function(ActivityType) {
                            return ActivityType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('activity-type', null, { reload: 'activity-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('activity-type.delete', {
            parent: 'activity-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/activity-type/activity-type-delete-dialog.html',
                    controller: 'ActivityTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ActivityType', function(ActivityType) {
                            return ActivityType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('activity-type', null, { reload: 'activity-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
