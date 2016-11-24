(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('extra-field', {
            parent: 'entity',
            url: '/extra-field',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ExtraFields'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/extra-field/extra-fields.html',
                    controller: 'ExtraFieldController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('extra-field-detail', {
            parent: 'entity',
            url: '/extra-field/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ExtraField'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/extra-field/extra-field-detail.html',
                    controller: 'ExtraFieldDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ExtraField', function($stateParams, ExtraField) {
                    return ExtraField.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'extra-field',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('extra-field-detail.edit', {
            parent: 'extra-field-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/extra-field/extra-field-dialog.html',
                    controller: 'ExtraFieldDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ExtraField', function(ExtraField) {
                            return ExtraField.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('extra-field.new', {
            parent: 'extra-field',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/extra-field/extra-field-dialog.html',
                    controller: 'ExtraFieldDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                extraFieldId: null,
                                dataItemId: null,
                                fieldName: null,
                                value: null,
                                importededId: null,
                                siteId: null,
                                dataItemType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('extra-field', null, { reload: 'extra-field' });
                }, function() {
                    $state.go('extra-field');
                });
            }]
        })
        .state('extra-field.edit', {
            parent: 'extra-field',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/extra-field/extra-field-dialog.html',
                    controller: 'ExtraFieldDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ExtraField', function(ExtraField) {
                            return ExtraField.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('extra-field', null, { reload: 'extra-field' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('extra-field.delete', {
            parent: 'extra-field',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/extra-field/extra-field-delete-dialog.html',
                    controller: 'ExtraFieldDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ExtraField', function(ExtraField) {
                            return ExtraField.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('extra-field', null, { reload: 'extra-field' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
