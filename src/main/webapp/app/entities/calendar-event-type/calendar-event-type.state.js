(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('calendar-event-type', {
            parent: 'entity',
            url: '/calendar-event-type',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CalendarEventTypes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/calendar-event-type/calendar-event-types.html',
                    controller: 'CalendarEventTypeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('calendar-event-type-detail', {
            parent: 'entity',
            url: '/calendar-event-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CalendarEventType'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/calendar-event-type/calendar-event-type-detail.html',
                    controller: 'CalendarEventTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CalendarEventType', function($stateParams, CalendarEventType) {
                    return CalendarEventType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'calendar-event-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('calendar-event-type-detail.edit', {
            parent: 'calendar-event-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/calendar-event-type/calendar-event-type-dialog.html',
                    controller: 'CalendarEventTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CalendarEventType', function(CalendarEventType) {
                            return CalendarEventType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('calendar-event-type.new', {
            parent: 'calendar-event-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/calendar-event-type/calendar-event-type-dialog.html',
                    controller: 'CalendarEventTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                calendarEventTypeId: null,
                                shortDescription: null,
                                iconImage: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('calendar-event-type', null, { reload: 'calendar-event-type' });
                }, function() {
                    $state.go('calendar-event-type');
                });
            }]
        })
        .state('calendar-event-type.edit', {
            parent: 'calendar-event-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/calendar-event-type/calendar-event-type-dialog.html',
                    controller: 'CalendarEventTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CalendarEventType', function(CalendarEventType) {
                            return CalendarEventType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('calendar-event-type', null, { reload: 'calendar-event-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('calendar-event-type.delete', {
            parent: 'calendar-event-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/calendar-event-type/calendar-event-type-delete-dialog.html',
                    controller: 'CalendarEventTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CalendarEventType', function(CalendarEventType) {
                            return CalendarEventType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('calendar-event-type', null, { reload: 'calendar-event-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
