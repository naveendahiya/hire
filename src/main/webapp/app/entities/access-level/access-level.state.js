(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('access-level', {
            parent: 'entity',
            url: '/access-level',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'AccessLevels'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/access-level/access-levels.html',
                    controller: 'AccessLevelController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('access-level-detail', {
            parent: 'entity',
            url: '/access-level/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'AccessLevel'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/access-level/access-level-detail.html',
                    controller: 'AccessLevelDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'AccessLevel', function($stateParams, AccessLevel) {
                    return AccessLevel.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'access-level',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('access-level-detail.edit', {
            parent: 'access-level-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/access-level/access-level-dialog.html',
                    controller: 'AccessLevelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AccessLevel', function(AccessLevel) {
                            return AccessLevel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('access-level.new', {
            parent: 'access-level',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/access-level/access-level-dialog.html',
                    controller: 'AccessLevelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                accessLevelId: null,
                                shortDescription: null,
                                longDescription: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('access-level', null, { reload: 'access-level' });
                }, function() {
                    $state.go('access-level');
                });
            }]
        })
        .state('access-level.edit', {
            parent: 'access-level',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/access-level/access-level-dialog.html',
                    controller: 'AccessLevelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AccessLevel', function(AccessLevel) {
                            return AccessLevel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('access-level', null, { reload: 'access-level' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('access-level.delete', {
            parent: 'access-level',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/access-level/access-level-delete-dialog.html',
                    controller: 'AccessLevelDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AccessLevel', function(AccessLevel) {
                            return AccessLevel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('access-level', null, { reload: 'access-level' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
